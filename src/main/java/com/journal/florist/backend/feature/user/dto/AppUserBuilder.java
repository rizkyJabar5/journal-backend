/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.journal.florist.backend.feature.user.model.AppUsers;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Builder
@Data
@JsonIgnoreProperties(value = "password")
public final class AppUserBuilder implements UserDetails {

    private Long userId;
    private String email;
    private String username;
    private String fullName;
    private String password;
    private Date joinDate;
    private String profileAvatar;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    private Collection<GrantedAuthority> authorities;

    public static AppUserBuilder buildUserDetails(AppUsers user) {
        Validate.notNull(user, "User must not be null");

        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRoles()
                .forEach(appRoles -> {
                    if (Objects.nonNull(appRoles.getRoleName())) {
                        authorities.add(new SimpleGrantedAuthority(appRoles.getRoleName().name()));
                    }
                });

        return AppUserBuilder.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .joinDate(user.getJoinDate())
                .profileAvatar(user.getProfileAvatar())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .authorities(authorities)
                .build();

    }
}


