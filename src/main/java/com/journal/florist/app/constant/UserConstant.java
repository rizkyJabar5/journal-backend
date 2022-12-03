package com.journal.florist.app.constant;


import java.util.regex.Pattern;

public final class UserConstant {

    private UserConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String DELETING_SELF_NOT_PERMITTED = "You can't delete it self .";
    public static final String EMAIL_ALREADY_TAKEN = "Email or Username has already taken by other user";

    // ----- User Constant ----- //
    public static final String SUCCESSFULLY_CREATE = "%s has been success add %s";
    public static final String EMAIL_OR_USERNAME_NOT_PROVIDED_MSG = "E-mail or username is not registered.";
    public static final String PASSWORD_MISMATCH = "Wrong password. Please try again";
    public static final String ROLE_IS_NOT_FOUND_MSG = "Error: Role %s is not found.";
    public static final String USER_NOT_FOUND_MSG = "Error: User %s is not found";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("(\\+62 ((\\d{3}([ -]\\d{3,})([- ]\\d{4,})?)|(\\d+)))|(\\(\\d+\\) \\d+)|\\d{3}( \\d+)+|(\\d+[ -]\\d+)|\\d+",
                    Pattern.CASE_INSENSITIVE);

}
