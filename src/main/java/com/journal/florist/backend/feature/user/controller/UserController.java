/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.controller;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Application Endpoint")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class  UserController {
    private final AppUserService appUserService;

    @Operation(summary = "Fetching all user with pagination")
    @GetMapping(value = "")
    public ResponseEntity<Page<AppUserBuilder>> getUsers(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer limit){
        Pageable filter = FilterableCrudService.getPageable(page - 1, limit);
        Page<AppUserBuilder> contentUsers = appUserService.getAllUsers(filter);

        return new ResponseEntity<>(contentUsers, HttpStatus.OK);
    }

    @Operation(summary = "Filter user by username")
    @GetMapping(value = "{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUsers> getByUsername(@PathVariable("username") String email, String username) {
        AppUsers content = appUserService.findByEmailOrUsername(email, username);

        return new ResponseEntity<>(content, HttpStatus.OK);
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
