package com.journal.florist.app.security;

import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
public final class SecurityUtils {

    private SecurityUtils() {
        throw new AssertionError("This class cannot be instantiated");
    }

    /**
     * Returns true if the user is authenticated.
     *
     * @param authentication the authentication object
     * @return if user is authenticated
     */
    public static boolean isAuthenticated(Authentication authentication) {
        return Objects.nonNull(authentication)
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public static AppUserBuilder getAuthenticatedUserDetails() {

        if (isAuthenticated()) {
            return (AppUserBuilder) getAuthentication().getPrincipal();
        }

        return null;
    }

    /**
     * Returns true if the user is authenticated.
     *
     * @return if user is authenticated
     */
    public static boolean isAuthenticated() {
        return isAuthenticated(getAuthentication());
    }

    /**
     * Retrieve the authentication object from the current session.
     *
     * @return authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Clears the securityContextHolder.
     */
    public static void clearAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    /**
     * Creates an authentication object with the userDetails then set authentication to
     * SecurityContextHolder.
     *
     * @param authenticationManager the userDetails
     */
    public static void authenticateUser(AuthenticationManager authenticationManager,
                                        String emails,
                                        String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                emails,
                password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    /**
     * For Jwt authentication object.
     * Creates an authentication object with the userDetails then set authentication to
     * SecurityContextHolder.
     * @param userDetails the userDetails
     */
    public static void authenticateUser(HttpServletRequest request, UserDetails userDetails) {

        if (Objects.nonNull(request) && Objects.nonNull(userDetails)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }



    /**
     * Validates that the user is neither disabled, locked nor expired.
     *
     * @param userDetails the user details
     */
    public static void validateUserDetailsStatus(UserDetails userDetails) {
        log.debug("User details {}", userDetails);

        if (!userDetails.isEnabled()) {
            throw new DisabledException("User is disabled");
        }
        if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("User is locked");
        }
        if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("User is account is is non active");
        }
        if (!userDetails.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("User credentials expired");
        }
    }
}