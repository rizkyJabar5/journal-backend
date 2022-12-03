/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.service;

import com.cloudinary.utils.ObjectUtils;
import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.CloudinaryConfig;
import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.exceptions.UserFriendlyDataException;
import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
import com.journal.florist.backend.feature.user.dto.RequestAppUser;
import com.journal.florist.backend.feature.user.dto.RequestUpdateAppUser;
import com.journal.florist.backend.feature.user.enums.ERole;
import com.journal.florist.backend.feature.user.model.AppRoles;
import com.journal.florist.backend.feature.user.model.AppUsers;
import com.journal.florist.backend.feature.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.journal.florist.app.constant.UserConstant.*;

@Transactional
@RequiredArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryConfig cloudinary;

    public Page<AppUsers> findAnyMatching(Optional<String> filter,
                                          Pageable pageable) {

        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return userRepository
                    .findByField(repositoryFilter, repositoryFilter, repositoryFilter, pageable);
        }

        return findUser(pageable);
    }

    public long countAnyMatching(Optional<String> filter) {

        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return userRepository
                    .countByField(repositoryFilter, repositoryFilter, repositoryFilter);
        }

        return userRepository.count();
    }

    public AppUsers getByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));
    }

    public AppUsers findByEmailOrUsername(String email, String username) {

        return Optional.ofNullable(userRepository.findByUsernameOrEmail(email, username))
                .orElseThrow(() -> new UsernameNotFoundException(EMAIL_OR_USERNAME_NOT_PROVIDED_MSG));
    }

    @Override
    public BaseResponse save(RequestAppUser request) {
        AppUserBuilder userLoggedIn = SecurityUtils.getAuthenticatedUserDetails();
        Authentication userLogged = SecurityUtils.getAuthentication();
        AppUsers user = new AppUsers();
        validateDuplicateEmailOrUsername(request.email(), request.username());

        user.setFullName(request.fullName());
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setHashedPassword(passwordEncoder.encode(request.hashedPassword()));

        addRoleUsers(user, request.rolesName());

        if (request.profilePicture() == null || request.profilePicture().isEmpty()) {
            Faker faker = new Faker();
            user.setProfileAvatar(faker.avatar().image());
        } else {
            String avatar = uploadImageToCloud(request.profilePicture());
            user.setProfileAvatar(avatar);
        }

        userRepository.save(user);
        AppUserBuilder mapper = AppUserBuilder.buildUserDetails(user);

        String createdUser = userLoggedIn != null ? userLoggedIn.getFullName() : userLogged.getName();
        return new BaseResponse(
                HttpStatus.CREATED,
                String.format(SUCCESSFULLY_CREATE, createdUser, user.getFullName()),
                mapper
        );
    }

    @Override
    public BaseResponse update(RequestUpdateAppUser request) {
        AppUsers persisted = getByUserId(request.userId());
        Set<AppRoles> updateUserRoles = new HashSet<>();

        String updateFullName = Objects.isNull(request.fullName())
                || request.fullName().isEmpty()
                ? persisted.getFullName()
                : request.fullName();
        String updateProfilePicture = Objects.isNull(request.profilePicture())
                || request.profilePicture().isEmpty()
                ? persisted.getProfileAvatar()
                : uploadImageToCloud(request.profilePicture());

        if (Objects.isNull(request.rolesName())
                || request.rolesName().isEmpty()) {
            persisted.getAuthorities();
        } else {
            request.rolesName().forEach(role -> {
                AppRoles roles = roleService.getRolesByName(ERole.valueOfLabel(role.getRoleName()));
                updateUserRoles.add(roles);
            });
        }

        persisted.setFullName(updateFullName);
        persisted.setProfileAvatar(updateProfilePicture);
        persisted.setRoles(updateUserRoles);

        userRepository.save(persisted);

        AppUserBuilder mapper = AppUserBuilder.buildUserDetails(persisted);
        return new BaseResponse(
                HttpStatus.ACCEPTED,
                UPDATE_PROFILE,
                mapper
        );
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
            if (!exists) {
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

    private void addRoleUsers(final AppUsers user, Set<ERole> requestRoles) {
        Set<AppRoles> userRoles = new HashSet<>();

        if (requestRoles == null) {
            AppRoles defaultRole = roleService.getRolesByName(ERole.ROLE_USER);
            userRoles.add(defaultRole);
            user.setRoles(userRoles);

            return;
        }

        requestRoles.forEach(role -> {
            AppRoles roles = roleService.getRolesByName(ERole.valueOfLabel(role.getRoleName()));
            userRoles.add(roles);
        });

        user.setRoles(userRoles);
    }

    private String uploadImageToCloud(MultipartFile image) {
        return cloudinary
                .upload(
                        image,
                        ObjectUtils.asMap(
                                "resourceType", "image",
                                "folder", "journal-backend/"))
                .get("url")
                .toString();
    }
}
