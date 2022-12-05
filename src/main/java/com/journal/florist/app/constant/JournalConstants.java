/*
 * Copyright (c) 2022.
 */

package com.journal.florist.app.constant;

public final class JournalConstants {

    private JournalConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static String[] concatenate(String... s) {
        return s;
    }

    public static final String SUCCESSFULLY_DELETE_OPERATION = "Successfully to deleted %s ";
    public static final String SUCCESSFULLY_ADD_NEW_ENTITY = "Successfully to add new %s ";
    public static final String NO_CONTENT = "No content found for %s ";
    public static final String MUST_BE_UNIQUE = "Name %s must be unique";
    public static final String UNIQUE_PHONE_NUMBER = "Phone number is already registered";
    public static final String NOT_FOUND_MSG = "Error: %s %s is not found";
    public static final String DELIVERY_NOTE_ALREADY_PRINTED = "Delivery note in order %s is already printed";
    public static final String ORDER_IS_NOT_FOR_DELIVERY = "Order has not info order shipment for delivery note";
    // AbstractCrudView
    public static final String CANNOT_BE_BLANK = "Field %s cannot be blank";
    public static final String NOT_VALID_FIELD = "%s is not valid";
    public static final String DELETE_MESSAGE = "Are you sure you want to delete the selected %s? This action cannot be undone.";

}
