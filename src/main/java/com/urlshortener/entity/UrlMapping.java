package com.urlshortener.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity 
@Table(
    name="url_mapping",
    indexes = {
        @Index(
            name="idx_url_user",
            columnList = "user_id"
        )
    }
)
public class UrlMapping {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column (
        name="short_code",
        nullable=false,
        unique=true,
        length=12
    )
    private String shortCode;


    @Column(
        name="original_url",
        nullable=false,
        length=2048
    )
    private String originalUrl;

    @Column(name="created_at")
    private Instant createdAt;

    @Column(name="expires_at")
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean active =true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    
}
