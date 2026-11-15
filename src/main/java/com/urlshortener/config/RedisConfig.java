package com.urlshortener.config;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.script.DefaultReactiveScriptExecutor;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration 
@EnableCaching 
public class RedisConfig {
    
    @Bean 
    public RedisCacheManager cacheManager(RedisConnectionFactory redisconnectionfactory){

        RedisCacheConfiguration config  = RedisCacheConfiguration.defaultCacheConfig()
                                          .entryTtl(Duration.ofHours(1))
                                          .disableCachingNullValues();
                                          
                                    return RedisCacheManager.builder(redisconnectionfactory)
                                      .cacheDefaults(config)
                                      .build();      
        
    }

    @Bean 
    public DefaultRedisScript<Long> rateLimitscript(){
        DefaultRedisScript<Long> defaultscript = new DefaultRedisScript<>();

        defaultscript.setLocation(
new ClassPathResource("scripts/rate_limit.lua")        );


defaultscript.setResultType(Long.class);
return defaultscript;
    }
}
