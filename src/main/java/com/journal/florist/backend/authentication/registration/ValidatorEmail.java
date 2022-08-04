package com.journal.florist.backend.authentication.registration;


import org.springframework.stereotype.Service;

import java.util.regex.Matcher;

import static com.journal.florist.app.constant.UserConstant.VALID_EMAIL_ADDRESS_REGEX;

@Service
public class ValidatorEmail {

    public boolean test(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

}
