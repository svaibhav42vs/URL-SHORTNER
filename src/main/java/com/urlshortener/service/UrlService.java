package com.urlshortener.service;

import java.net.URI;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.urlshortener.dto.CreateUrlRequest;
import com.urlshortener.dto.CreateUrlResponse;
import com.urlshortener.entity.UrlMapping;
import com.urlshortener.exception.UrlNotFoundException;
import com.urlshortener.repository.UrlRepository;

import jakarta.transaction.Transactional;

@Service 
public class UrlService {

    private final UrlRepository urlRepository;
    private final ShortCodeGenerator codeGenerator;

    @Value ("${app.base-url}")
    private String baseUrl;

    public UrlService(UrlRepository urlRepository, ShortCodeGenerator codeGenerator) {
        this.urlRepository = urlRepository;
        this.codeGenerator = codeGenerator;
    }

    @Transactional 
    public CreateUrlResponse createShortUrl(CreateUrlRequest request){
        String originalUrl = validateUrl(request.originalUrl());

        String shortCode = generateUniqueCode();

        UrlMapping mapping = new UrlMapping();
        mapping.setShortCode(shortCode);
        mapping.setOriginalUrl(originalUrl);
        mapping.setCreatedAt(Instant.now());
        mapping.setActive(true);

        UrlMapping saved = urlRepository.save(mapping);

        return new CreateUrlResponse(saved.getShortCode(), baseUrl+"/"+saved.getShortCode(), saved.getOriginalUrl(), saved.getCreatedAt());
    } 

    @Transactional
    public String resolveShortUrl(String shortCode) {

        UrlMapping mapping = urlRepository
            .findByShortCode(shortCode)
            .orElseThrow(() ->
                new UrlNotFoundException(
                    "Short URL not found"
                )
            );

        if (!mapping.isActive()) {
            throw new UrlNotFoundException(
                "Short URL is inactive"
            );
        }

        if (mapping.getExpiresAt() != null &&
            mapping.getExpiresAt().isBefore(Instant.now())) {

            throw new UrlNotFoundException(
                "Short URL has expired"
            );
        }

        return mapping.getOriginalUrl();
    }

    private String generateUniqueCode() {

        for (int attempt = 0; attempt < 5; attempt++) {

            String code = codeGenerator.generate();

            if (!urlRepository.existsByShortCode(code)) {
                return code;
            }
        }

        throw new IllegalStateException(
            "Unable to generate unique short code"
        );
    }

    private String validateUrl(String value) {

        try {
            URI uri = URI.create(value.trim());

            String scheme = uri.getScheme();

            if (!uri.isAbsolute()
                    || uri.getHost() == null
                    || !("http".equalsIgnoreCase(scheme)
                    || "https".equalsIgnoreCase(scheme))) {
                throw new IllegalArgumentException(
                    "Only valid HTTP and HTTPS URLs are allowed"
                );
            }

            return uri.toASCIIString();

        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                "Invalid original URL"
            );
        }
    }
    
}
