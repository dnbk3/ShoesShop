package vn.btl.hdvapigateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import vn.btl.hdvapigateway.repository.TokenRedisRepository;

@Service
public class TokenService {

    @Autowired
    private TokenRedisRepository tokenRedisRepository;

    public Mono<Boolean> exitsToken(String id, String token){
        return tokenRedisRepository.exitsToken(id, token);
    }
}
