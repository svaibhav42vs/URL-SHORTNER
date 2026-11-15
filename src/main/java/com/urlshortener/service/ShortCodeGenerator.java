package com.urlshortener.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component 
public class ShortCodeGenerator {
    
    private static final String BASE62 =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final int CODE_LENGTH = 8;

    private final SecureRandom random = new SecureRandom();

    public String generate() {

        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(BASE62.length());
            code.append(BASE62.charAt(index));
        }

        return code.toString();
    }
}
