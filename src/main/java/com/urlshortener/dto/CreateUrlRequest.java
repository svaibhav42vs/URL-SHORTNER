package com.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUrlRequest(
    @NotBlank(message = "Original URL is required")
    @Size(max = 2048, message = "URL is too long")
    String originalUrl
) {
}
