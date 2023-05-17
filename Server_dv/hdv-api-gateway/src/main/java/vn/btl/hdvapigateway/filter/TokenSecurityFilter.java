package vn.btl.hdvapigateway.filter;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.btl.hdvapigateway.excepition.JwtTokenMalformedException;
import vn.btl.hdvapigateway.excepition.JwtTokenMissingException;
import vn.btl.hdvapigateway.service.TokenService;
import vn.btl.hdvapigateway.utils.JwtUtil;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class TokenSecurityFilter implements GlobalFilter, Ordered {

    @Value("${gateway.api.public}")
    private List<String> publicApi;

    private static final String TOKEN_PATTERN = "Bearer (.*)";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @PostConstruct
    private void postInit(){
        for(String api: publicApi){
            log.info("Public api: {}", api);
        }
    }

    public static String getAccessToken(String text){
        Pattern pattern = Pattern.compile(TOKEN_PATTERN);
        Matcher matcher = pattern.matcher(text);
        if(matcher.find())
            return matcher.group(1);
        return null;
    }

    private boolean checkPath(String path, HttpMethod method){
        PathPatternParser pathPatternParser = new PathPatternParser();
        Predicate<String> isApiSecured = r -> publicApi.stream()
                .noneMatch(regexPath -> {
                    String [] re = regexPath.split("\\|\\|\\|");
                    String methodRegex = null;
                    String regex;
                    if(re.length>=2){
                        methodRegex = re[0];
                        regex = re[1];
                    }
                    else {
                        regex = regexPath;
                    }
                    boolean checkMethod = true;
                    if(methodRegex != null)
                        checkMethod = method.matches(methodRegex);
                    return checkMethod && pathPatternParser.parse(regex).matches(PathContainer.parsePath(path));
                });
        return isApiSecured.test(path);
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if(checkPath(request.getPath().value(), request.getMethod())){
            if(!request.getHeaders().containsKey("Authorization")){
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                byte[] bytes = "{\"status\":{\"code\":\"CORE-401\",\"message\":\"Không có quyền truy cập.\",\"label\":\"ERROR_CORE_PERMISSION_DENIED\"},\"data\":null}".getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return response.writeWith(Flux.just(buffer)).flatMap(unused -> response.setComplete());
            }
            String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            if(token.contains("Bearer"))
                token = getAccessToken(token);

            try {
                jwtUtil.validationToken(token);
                Claims claims = jwtUtil.getClaims(token);
                String finalToken = token;
                return tokenService.exitsToken((String) claims.get("user_id"), token).flatMap(exits -> {
                    if(exits){
                        ServerHttpRequest newRequest = exchange.getRequest().mutate()
                                .header("token", finalToken)
                                .header("user_id", (String) claims.get("user_id"))
                                .header("phone", (String) claims.get("phone")).build();
                        return chain.filter(exchange.mutate().request(newRequest).build());
                    }
                    else {
                        log.info("token has removed");
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                        byte[] bytes = "{\"status\":{\"code\":\"CORE-405\",\"message\":\"Mã truy cập hết hạn.\"},\"data\":null}".getBytes(StandardCharsets.UTF_8);
                        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(bytes);
                        return response.writeWith(Flux.just(dataBuffer)).flatMap(unused -> response.setComplete());
                    }
                });

            }catch (JwtTokenMalformedException | JwtTokenMissingException e){
                log.error("Token is invalid: {}", e.getMessage());
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.OK);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                byte[] bytes = "{\"status\":{\"code\":\"CORE-405\",\"message\":\"Mã truy cập hết hạn.\"},\"data\":null}".getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return response.writeWith(Flux.just(buffer)).flatMap(unused -> response.setComplete());
            }
        }
        else {
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
