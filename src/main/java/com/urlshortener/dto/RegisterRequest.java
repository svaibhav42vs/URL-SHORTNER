package com.urlshortener.dto;

public record RegisterRequest(
    String name,
    String email,
    String password
) {
    
}
