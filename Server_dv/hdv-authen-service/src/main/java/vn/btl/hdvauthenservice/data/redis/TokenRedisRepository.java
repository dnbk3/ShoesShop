package vn.btl.hdvauthenservice.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
public class TokenRedisRepository {
    private final static String REDIS_PREFIX = "token_";

    @Value("${token.expired.time.in.minutes}")
    private Integer delta;

    private int MAX_DEVICE = 20;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String id, String token) {
        String key = REDIS_PREFIX + id;
        Objects.requireNonNull(token);
        Long size = redisTemplate.opsForList().size(key);
        redisTemplate.opsForList().leftPush(key, token);
        redisTemplate.expire(key, delta, TimeUnit.MINUTES);
        if (Objects.nonNull(size) && size > MAX_DEVICE) {
            redisTemplate.opsForList().rightPop(key);
        }
    }

    public Boolean deleteToken(Long userId, String token) {
        String key = REDIS_PREFIX + userId;
        Long edited = redisTemplate.opsForList().remove(key, 1, token);
        return edited != null && edited > 0;
    }
}
