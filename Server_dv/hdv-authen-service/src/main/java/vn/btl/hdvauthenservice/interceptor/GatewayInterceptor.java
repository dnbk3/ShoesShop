package vn.btl.hdvauthenservice.interceptor;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class GatewayInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GatewayInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String correlationId = request.getHeader("requestId");
        String ipClient = request.getRemoteAddr();
        if (correlationId == null) {
            correlationId = RandomStringUtils.randomAlphabetic(6);
        }
        ThreadContext.put("requestId", correlationId);
        logger.info("========== Start process request [{}]:[{}] from IP [{}]", request.getMethod(), request.getServletPath(), ipClient);
        request.setAttribute("requestId", System.currentTimeMillis());
        return verifyRequest(request);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long startTime = (Long) request.getAttribute("requestId");
        Long processTime = System.currentTimeMillis() - startTime;
        logger.info("========= End process request [{}]:[{}] with [{}]. Processing time [{}]", request.getMethod(), request.getServletPath(), response.getStatus(), processTime);
    }

    private boolean verifyRequest(HttpServletRequest request) {
        String userId = request.getHeader("user_id");
        if (userId == null) {
            userId = request.getHeader("customer_id");
        }
        String phone = request.getHeader("phone");
        String token = request.getHeader("token");

        Payload payload = new Payload();
        if (!StringUtils.isBlank(userId)) {
            payload.setCustomerId(Long.valueOf(userId));
            payload.setPhone(phone);
            payload.setToken(token);

            request.setAttribute("payload", payload);
            logger.info("Request validated. Start forward request to backend");
        }
        return true;
    }
}
