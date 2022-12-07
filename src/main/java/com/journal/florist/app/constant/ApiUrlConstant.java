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
    public static final String CUSTOMER_URL = "/api/v1/customers";
    public static final String ORDER_URL = "/api/v1/orders";
    public static final String REPORT_SALES = "/api/v1/sales";
    public static final String PRINTED_DELIVERY_NOTE = ORDER_URL + "/printed-delivery-note";
    public static final String PURCHASE_URL = "/api/v1/purchase";
    public static final String SUPPLIER_URL = "/api/v1/suppliers";
    public static final String EXPENSE_URL = "/api/v1/expenses";
    public static final String FINANCE_URL = "/api/v1/finances";
    public static final String PAYMENT_URL = "/api/v1/payments";
    public static final String USER_URL = "/api/v1/users";
    public static final String SUMMARY_URL = "/api/v1/summaries";
    public static final String LOGIN_URL = "/api/v1/auth/login";
    public static final String LOGIN_FAILURE_URL = "/login?error";
    public static final String LOGOUT_URL = "/";

}
