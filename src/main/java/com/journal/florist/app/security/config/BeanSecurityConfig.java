/*
 * Copyright (c) 2022.
 */

package com.journal.florist.app.security.config;

import com.journal.florist.app.utils.HasLogger;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.feature.user.enums.ERole;
import com.journal.florist.backend.feature.user.model.AppRoles;
import com.journal.florist.backend.feature.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@RequiredArgsConstructor
@Configuration
public class BeanSecurityConfig implements HasLogger {

    private final RoleService roleService;

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public void addRole() {
        for (ERole role : ERole.values()) {
            try {
                AppRoles appRoles = roleService.getRolesByName(role);
                getLogger().info("{} is found", appRoles);
            } catch (AppBaseException e) {
                AppRoles roles = new AppRoles();
                if (Objects.equals(ERole.ROLE_CASHIER, role)) {
                    roles.setRoleName(role);
                    roles.setRoleDescription("Cashier role to manage sales");
                }
                if (Objects.equals(ERole.ROLE_OWNER, role)) {
                    roles.setRoleName(role);
                    roles.setRoleDescription("Owner role to view income reports and expense reports");
                }
                if (Objects.equals(ERole.ROLE_SUPERADMIN, role)) {
                    roles.setRoleName(role);
                    roles.setRoleDescription("The highest Super admin role for this application.");
                }
                if (Objects.equals(ERole.ROLE_USER, role)) {
                    roles.setRoleName(role);
                    roles.setRoleDescription("Global users to view dashboard and new orders coming");
                }
                getLogger().info("Success to persist {}", roles.getRoleName());
                roleService.saveNewRole(roles);
            }
        }
    }

}
