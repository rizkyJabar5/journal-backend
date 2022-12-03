/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
import com.journal.florist.backend.feature.user.dto.RequestAppUser;
import com.journal.florist.backend.feature.user.dto.RequestUpdateAppUser;
import com.journal.florist.backend.feature.user.model.AppUsers;
import com.journal.florist.backend.feature.user.service.AppUserService;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.journal.florist.app.constant.ApiUrlConstant.USER_URL;

@Tag(name = "User Application Endpoint")
@RestController
@RequiredArgsConstructor
@RequestMapping(USER_URL)
public class UserController {
    private final AppUserService appUserService;

    @Operation(summary = "Fetching all user with pagination")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getUsers(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer limit) {
        Pageable filter = FilterableCrudService.getPageable(page - 1, limit);
        Page<AppUserBuilder> userPage = appUserService.getAllUsers(filter);

        BaseResponse response = new BaseResponse(
                HttpStatus.FOUND,
                "Fetching all users",
                userPage
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Filter user by username")
    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> getByUsername(
            @RequestParam(name = "username") String email,
            String username) {
        AppUsers appUsers = appUserService.findByEmailOrUsername(email, username);
        AppUserBuilder userDetails = AppUserBuilder.buildUserDetails(appUsers);

        BaseResponse response = new BaseResponse(
                HttpStatus.FOUND,
                String.format("User %s is found", username),
                userDetails
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Add new user")
    @PostMapping(value = "/add-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<BaseResponse> addNewUser(@ModelAttribute RequestAppUser request){
        BaseResponse response = appUserService.save(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update-user")
    public ResponseEntity<BaseResponse> saveRole(@ModelAttribute RequestUpdateAppUser request){
        BaseResponse response = appUserService.update(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
//
//    @PostMapping("/role/addtouser`")
//    public ResponseEntity<?> addToUser(@RequestBody RoleToUSerForm form){
//        userService.addRoleToUser(form.getUsername(),form.getRoleName());
//        return ResponseEntity.ok().build();
//    }
}
