/*
 * Copyright (c) 2022.
 */

package com.journal.florist.app.security.config;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.app.common.utils.converter.RequestParameterConverter;
import com.journal.florist.backend.feature.user.enums.ERole;
import com.journal.florist.backend.feature.user.model.AppRoles;
import com.journal.florist.backend.feature.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class BeanSecurityConfig implements WebMvcConfigurer, HasLogger {

    private final RoleService roleService;

    private final ListableBeanFactory beanFactory;

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addFormatters(@NotNull FormatterRegistry registry) {
        Map<String, Object> components = beanFactory.getBeansWithAnnotation(RequestParameterConverter.class);
        components.values()
                .parallelStream()
                .forEach(c -> {
                    if(c instanceof Converter<?, ?>) {
                        registry.addConverter((Converter<?, ?>) c);
                    }
                });
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = uploadPath();
        registry.addResourceHandler("/generated-reports/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }

    /*CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    private String uploadPath() {
        Path path = Paths.get("./generated-reports");
        return path.toFile().getAbsolutePath();
    }

    @Bean
    public void addRole() {
        for (ERole role : ERole.values()) {
            try {
                AppRoles appRoles = roleService.getRolesByName(role);
                getLogger().info("{} is found", appRoles);
            } catch (RuntimeException e) {
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
                if(Objects.equals(ERole.ROLE_ADMIN, role)){
                    roles.setRoleName(role);
                    roles.setRoleDescription("Administration role to manage financial and orders");
                }
                getLogger().info("Success to persist {}", roles.getRoleName());
                roleService.saveNewRole(roles);
            }
        }
    }

}
