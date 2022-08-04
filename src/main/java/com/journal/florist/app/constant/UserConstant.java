package com.journal.florist.app.constant;


import java.util.regex.Pattern;

public final class UserConstant {

    private UserConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String DELETING_SELF_NOT_PERMITTED = "You can't delete it self .";
    public static final String EMAIL_ALREADY_TAKEN = "E-mail has already taken by other user";

    // ----- User Constant ----- //
    public static final String EMAIL_OR_USERNAME_NOT_PROVIDED_MSG = "Error: E-mail %s or username %s is not registered.";
    public static final String ROLE_IS_NOT_FOUND_MSG = "Error: Role %s is not found.";
    public static final String USER_NOT_FOUND_MSG = "Error: User %s is not found";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

}
