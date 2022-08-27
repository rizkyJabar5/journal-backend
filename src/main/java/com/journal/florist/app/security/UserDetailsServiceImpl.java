package com.journal.florist.app.security;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
import com.journal.florist.backend.feature.user.model.AppUsers;
import com.journal.florist.backend.feature.user.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService, HasLogger {

    private final AppUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUsers appUser = userService.findByEmailOrUsername(username, username);

        getLogger().info("User present with username or email: {}", username);
        return AppUserBuilder.buildUserDetails(appUser);
    }

}
