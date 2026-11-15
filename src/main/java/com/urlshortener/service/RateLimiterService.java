
package com.urlshortener.service;

import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> redisScript;

    private static final int MAX_REQUESTS = 10;
private static final int WINDOW_SECONDS = 60;

    public RateLimiterService(
            StringRedisTemplate redisTemplate,
            DefaultRedisScript<Long> redisScript) {

        this.redisTemplate = redisTemplate;
        this.redisScript = redisScript;
    }

    public boolean isAllowed(
            String userId) {

        String key = "rate_limit:" + userId;

        Long count = redisTemplate.execute(
                redisScript,
                List.of(key),
                String.valueOf(WINDOW_SECONDS)
        );

        return count != null && count <= MAX_REQUESTS;
    }
}
