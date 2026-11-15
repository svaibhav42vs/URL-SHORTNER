
package com.urlshortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortener.dto.CreateUrlRequest;
import com.urlshortener.dto.CreateUrlResponse;
import com.urlshortener.service.UrlService;
import com.urlshortener.service.RateLimiterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
public class UrlController {

    private final UrlService urlservice;

    private final RateLimiterService rateLimiterService;

    @PostMapping("/api/v1/urls")
    public ResponseEntity<CreateUrlResponse> createUrl(
            @Valid @RequestBody CreateUrlRequest request) {

        // Temporary user ID
        String userId = "user101";

        // Check rate limit
        if (!rateLimiterService.isAllowed(userId)) {

            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .build();
        }

        CreateUrlResponse response =
                urlservice.createShortUrl(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
