package com.journal.florist.app.security.config;

import com.journal.florist.app.security.jwt.AuthEntryPointJwt;
import com.journal.florist.app.security.jwt.AuthorizationFilterDenied;
import com.journal.florist.app.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.journal.florist.app.constant.ApiUrlConstant.*;

@Configuration
@RequiredArgsConstructor
public class ApiWebSecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final AuthEntryPointJwt authEntryPointJwt;
    private final AuthorizationFilterDenied accessDenied;
    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    @Order(1)
    SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

        http.cors();
        http.csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDenied)
                .authenticationEntryPoint(authEntryPointJwt)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //PUBLIC End points
        http.authorizeRequests()
                .antMatchers(AUTHENTICATION_URL + "/**",
                        SWAGGER_API,
                        SWAGGER_API_DOCS,
                        HOME_PAGE + "/**").permitAll()

                //Authenticated request
                .anyRequest()
                .authenticated();

        http.authenticationManager(authenticationManager);

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
