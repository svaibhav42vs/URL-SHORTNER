package com.urlshortener.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import com.urlshortener.service.RedirectCacheService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor  
public class RedirectController {
    private final RedirectCacheService redirectcacheservice;

    @GetMapping ("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable  String shortCode){
System.out.println("HIT");
        String url = redirectcacheservice.getOriginalUrl(shortCode);
       HttpHeaders headers = new HttpHeaders();
       headers.add("Location",url);
       return new ResponseEntity<>(
                headers,
                HttpStatus.FOUND
        );
    }

}
