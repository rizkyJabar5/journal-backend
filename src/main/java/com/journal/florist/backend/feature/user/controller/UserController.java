/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
import com.journal.florist.backend.feature.user.model.AppUsers;
import com.journal.florist.backend.feature.user.service.AppUserService;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Application Endpoint")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final AppUserService appUserService;

    @Operation(summary = "Fetching all user with pagination")
    @GetMapping
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

    public ResponseEntity<BaseResponse> getByUsername(
            @RequestParam(name = "username") String email,
            String username) {
        AppUsers appUsers = appUserService.findByEmailOrUsername(email, username);

        BaseResponse response = new BaseResponse(
                HttpStatus.FOUND,
                String.format("User %s is found", username),
                appUsers
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping("/user/save")
//    public ResponseEntity<User> saveUser(@RequestBody User user){
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath(). path("/api/user/save").toUriString());
//        return ResponseEntity.created(uri).body(userService.save(user));
//    }

//    @PostMapping("/role/save")
//    public ResponseEntity<Role> saveRole(@RequestBody Role role){
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
//        return ResponseEntity.created(uri).body(userService.saveRole(role));
//    }
//
//    @PostMapping("/role/addtouser`")
//    public ResponseEntity<?> addToUser(@RequestBody RoleToUSerForm form){
//        userService.addRoleToUser(form.getUsername(),form.getRoleName());
//        return ResponseEntity.ok().build();
//    }
}
