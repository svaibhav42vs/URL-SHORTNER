package com.urlshortener.repository;

public interface UrlRepository  extends org.springframework.data.jpa.repository.JpaRepository<com.urlshortener.entity.UrlMapping, Long> {
    java.util.Optional<com.urlshortener.entity.UrlMapping> findByShortCode(String shortCode);
        boolean existsByShortCode(String shortCode);

    
}
