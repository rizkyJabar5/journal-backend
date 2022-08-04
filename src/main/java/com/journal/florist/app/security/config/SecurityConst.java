package com.journal.florist.app.security.config;

public final class SecurityConst {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String MUST_BE_AUTHENTICATED = "You must be authenticated";
    public static final String BLANK_USERNAME = "Field is required can't be blank.";
    public static final String TOKEN_CREATED_SUCCESS = "Token created successfully as {}.";
    public static final long EXPIRATION_TIME_JWT = 864000000;
}
