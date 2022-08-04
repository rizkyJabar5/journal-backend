/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.service;

import com.journal.florist.app.utils.BaseResponse;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.exceptions.UserFriendlyDataException;
import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
import com.journal.florist.backend.feature.user.enums.ERole;
import com.journal.florist.backend.feature.user.model.AppRoles;
import com.journal.florist.backend.feature.user.model.AppUsers;
import com.journal.florist.backend.feature.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static com.journal.florist.app.constant.UserConstant.*;

@Transactional
@RequiredArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Page<AppUsers> findAnyMatching(Optional<String> filter,
                                          Pageable pageable) {

        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return userRepository
                    .findByField(repositoryFilter, repositoryFilter, repositoryFilter, pageable);
        } else {
            return findUser(pageable);
        }
    }

    public long countAnyMatching(Optional<String> filter) {

        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return userRepository
                    .countByField(repositoryFilter, repositoryFilter, repositoryFilter);
        }

        return userRepository.count();
    }

    public Optional<AppUsers> getByUserId(Long id) {
        return userRepository.findById(id);
    }

    public AppUsers findByEmailOrUsername(String email, String username) {

        return Optional.ofNullable(userRepository.findByUsernameOrEmail(email, username))
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format(EMAIL_OR_USERNAME_NOT_PROVIDED_MSG, email, username)));
    }

    @Override
    public BaseResponse save(AppUsers appUser, AppUsers entity) {

        validateDuplicateEmailOrUsername(entity.getFullName(), entity.getEmail());
        return null;
    }

    @Override
    public BaseResponse update(AppUsers appUser, AppUsers entity) {
        return null;
    }

    @Override
    public BaseResponse delete(AppUsers appUser, AppUsers appUserToDelete) {
        throwIfDeletingSelf(appUser, appUserToDelete);
        return null;
    }

    public Page<AppUserBuilder> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(AppUserBuilder::buildUserDetails);

    }

    /**
     * For creating a default user with role super admin
     */
    @Bean
    public void addDefaultUser() {
        try {
            String email = "admin@florist.com";
            String username = "admin";
            boolean exists = userRepository.existsByUsernameOrEmail(email, username);
            if(!exists) {
                AppUsers users = new AppUsers();
                users.setEmail("admin@florist.com");
                users.setFullName("Super admin");
                users.setUsername("admin");
                users.setHashedPassword(passwordEncoder.encode("admin"));
                AppRoles roles = roleService.getRolesByName(ERole.ROLE_SUPERADMIN);
                users.setRoles(Collections.singleton(roles));
                Faker faker = new Faker();
                users.setProfileAvatar(faker.avatar().image());

                getLogger().info("Successfully created default user {}", users.getUsername());
                userRepository.save(users);
            }
            getLogger().info("{} user found.", username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateDuplicateEmailOrUsername(String email, String username) {
        boolean present = userRepository.existsByUsernameOrEmail(email, username);
        if (present) {
            getLogger().info("{} has already taken by other user", email.toLowerCase());
            throw new IllegalException(EMAIL_ALREADY_TAKEN);
        }
    }

    private Page<AppUsers> findUser(Pageable pageable) {
        return userRepository.findBy(pageable);
    }

    private void throwIfDeletingSelf(AppUsers appUser, AppUsers appUserToDelete) {
        if (appUser.equals(appUserToDelete)) {
            throw new UserFriendlyDataException(DELETING_SELF_NOT_PERMITTED);
        }
    }

}
