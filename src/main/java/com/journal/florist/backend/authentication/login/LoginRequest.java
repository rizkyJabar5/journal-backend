package com.journal.florist.backend.authentication.login;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {

    private String username;
    private String password;
}
