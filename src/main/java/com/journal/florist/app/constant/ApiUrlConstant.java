package com.journal.florist.app.constant;

public final class ApiUrlConstant {

    private ApiUrlConstant() {
        throw new IllegalStateException("Utility class");
    }

    // ----- Url Constants ----- //
    public static final String AUTHENTICATION_URL = "/api/v1/auth";
    public static final String SWAGGER_API_DOCS = "/v3/api-docs/**";
    public static final String SWAGGER_API = "/swagger-ui/**";
    public static final String HOME_PAGE = "/api/v1/home";
    public static final String PRODUCTS_URL = "/api/v1/products";
    public static final String CATEGORY_URL = "/api/v1/categories";

    public static final String LOGIN_URL = "/api/v1/auth/login";
    public static final String LOGIN_FAILURE_URL = "/login?error";
    public static final String LOGOUT_URL = "/";

}
