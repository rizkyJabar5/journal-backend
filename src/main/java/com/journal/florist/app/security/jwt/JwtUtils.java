/*
 * Copyright (c) 2022.
 */

package com.journal.florist.app.security.jwt;


import com.journal.florist.app.common.utils.HasLogger;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.journal.florist.app.security.config.SecurityConst.*;

@RequiredArgsConstructor
@Component
public class JwtUtils implements HasLogger {
    private final SecretKey secretKey;
    private final UserDetailsService userDetailsService;

    /**
     * Generate a JwtToken for the specified email.
     *
     * @param email the email
     * @return the token
     */
    public String generateJwtToken(String email) {

        Validate.notBlank(email, BLANK_USERNAME);

        UserDetails users = userDetailsService.loadUserByUsername(email);

        String jwtToken = Jwts.builder()
                .setSubject(email)
                .claim("authorities", users.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_JWT))
                .signWith(secretKey)
                .compact();

        getLogger().info(TOKEN_CREATED_SUCCESS, jwtToken);
        return jwtToken;
    }

    /**
     * Retrieve username from the token.
     *
     * @param token the token
     * @return the username
     */
    public String getUsernameFromToken(String token) {
        Validate.notBlank(token, "Token cannot be blank");

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Retrieves the jwt token from the request cookie or request header if present and valid.
     *
     * @param request the httpRequest
     * @return the jwt token
     */
    public String getJwtToken(HttpServletRequest request) {

        return getJwtFromRequest(request);
    }

    /**
     * Validates the Jwt token passed to it.
     *
     * @param token the token
     * @return if valid or not
     */
    public boolean isValidJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            getLogger().error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            getLogger().error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            getLogger().error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            getLogger().error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            getLogger().error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves the jwt token from the request header if present and valid.
     *
     * @param request the httpRequest
     * @return the jwt token
     */
    private String getJwtFromRequest(HttpServletRequest request) {

        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotBlank(headerAuth)
                && headerAuth.startsWith(TOKEN_PREFIX)) {
            return headerAuth.substring(7); // 10 day of expired at token
        }
        return null;
    }
}
