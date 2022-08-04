package com.journal.florist.backend.authentication.login;

import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.app.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.journal.florist.app.constant.ApiUrlConstant.AUTHENTICATION_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION_URL)
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginJwtResponse> authenticationUserLogin(
            @Valid @RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        SecurityUtils.authenticateUser(
                authenticationManager,
                username,
                loginRequest.getPassword());

        String jwtToken = jwtUtils.generateJwtToken(username);

        return ResponseEntity.ok().body(LoginJwtResponse.buildJwtResponse(jwtToken));
    }

}
