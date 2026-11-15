package com.urlshortener.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.urlshortener.entity.UrlMapping;
import com.urlshortener.exception.UrlNotFoundException;
import com.urlshortener.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RedirectCacheService {
    private final UrlRepository urlrepo;

    @Cacheable(value="redirects",key="#shortCode",unless="#result == null")
    public String getOriginalUrl(String shortCode){

        UrlMapping urlmapping = urlrepo.findByShortCode(shortCode)
                                .orElseThrow(() -> new UrlNotFoundException("Short code not found: " + shortCode));

                               return urlmapping.getOriginalUrl(); 
    }
}
