//package com.journal.florist.backend.authentication.registration;
//
//
//import com.journal.florist.backend.feature.user.service.AppUserService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//import static com.journal.florist.app.constant.JournalConstants.EMAIL_ALREADY_TAKEN;
//
//
//@RestController
//@RequestMapping("/api/v1/auth/registration")
//@AllArgsConstructor
//public class RegistrationController {
//
//    private final RegistrationService registrationService;
//
//    private AppUserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<String> postRegistration(@Valid @RequestBody  request) {
//
//        boolean existsByEmail = userService.findByUsername(request.getUsername());
//        if (existsByEmail) {
//            return ResponseEntity.badRequest().body(
//                    String.format(EMAIL_ALREADY_TAKEN, request.getEmail())
//            );
//        }
//
//        boolean existsUsername = userService.existsByUsername(request.getUsername());
//        if (existsUsername) {
//            return ResponseEntity.badRequest().body(
//                    String.format(USERNAME_ALREADY_EXISTS_MSG, request.getUsername()));
//        }
//
//        return ResponseEntity.ok().body(registrationService.register(request));
//
//    }
//
//    @GetMapping
//    public ResponseEntity<String> getRegistration() {
//
//        return null;
//    }
//
//
//}
