package vn.btl.hdvapigateway.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class TokenRedisRepository {

    private final static String REDIS_PREFIX = "token_";

    private final ReactiveRedisOperations<String, String> reactiveRedisOperations;

    public Mono<Boolean> exitsToken(String id, String token){
        String key = REDIS_PREFIX + id;
        return reactiveRedisOperations.opsForList().range(key, 0 , -1).hasElement(token);
    }

}
