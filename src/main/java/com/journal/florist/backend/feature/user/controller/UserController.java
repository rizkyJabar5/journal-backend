/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.controller;

import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
import com.journal.florist.backend.feature.user.model.AppUsers;
import com.journal.florist.backend.feature.user.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class  UserController {
    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<Page<AppUserBuilder>> getUsers(Pageable pageable){
        Page<AppUserBuilder> contentUsers = appUserService.getAllUsers(pageable);
        return ResponseEntity.ok()
                .body(contentUsers);
    }

    @GetMapping("{username}")
    public ResponseEntity<AppUsers> getByUsername(@PathVariable("username") String email, String username) {
        return ResponseEntity.ok().body(appUserService.findByEmailOrUsername(email, username));
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
