package com.urlshortener.dto;

public record LoginRequest(
    String email,
    String password
) {
    
}
