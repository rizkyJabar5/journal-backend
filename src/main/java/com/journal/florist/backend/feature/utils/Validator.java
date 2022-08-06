package com.journal.florist.backend.feature.utils;


import org.springframework.stereotype.Service;

import java.util.regex.Matcher;

import static com.journal.florist.app.constant.UserConstant.VALID_EMAIL_ADDRESS_REGEX;
import static com.journal.florist.app.constant.UserConstant.VALID_PHONE_NUMBER_REGEX;

@Service
public class Validator {

    public boolean isValidEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public boolean isValidPhoneNumber(String phone) {
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phone);
        return matcher.find();
    }

}
