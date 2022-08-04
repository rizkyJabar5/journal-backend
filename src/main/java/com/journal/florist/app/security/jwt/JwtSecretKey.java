/*
 * Copyright (c) 2022.
 */

package com.journal.florist.app.security.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
