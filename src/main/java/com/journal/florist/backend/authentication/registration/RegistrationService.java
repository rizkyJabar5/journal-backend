//package com.journal.florist.backend.authentication.registration;
//
//
//import com.journal.florist.app.utils.HasLogger;
//import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
//import com.journal.florist.backend.feature.user.service.AppUserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class RegistrationService implements HasLogger {
//
//    private final AppUserService userService;
//    private final ValidatorEmail validatorEmail;
//
//    public String register(AppUserBuilder request) {
//
//        boolean isValidEmail = validatorEmail
//                .test(request.getEmail());
//
//        if(!isValidEmail){
//            throw new IllegalStateException("Email has not valid");
//        }
//
//        return userService.save(
//                new AppUser(
//                        request.getUsername(),
//                        request.getName(),
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//    }
//
//}
