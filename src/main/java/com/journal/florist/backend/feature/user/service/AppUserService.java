/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
import com.journal.florist.backend.feature.user.model.AppUsers;
import com.journal.florist.app.common.messages.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface AppUserService extends HasLogger {

    Page<AppUsers> findAnyMatching(Optional<String> filter, Pageable pageable);
    long countAnyMatching(Optional<String> filter);
    Optional<AppUsers> getByUserId(Long id);
    Page<AppUserBuilder> getAllUsers(Pageable pageable);
    AppUsers findByEmailOrUsername(String email, String username);

    //    ------------CRUD SERVICE--------------//
    BaseResponse save(AppUsers appUser, AppUsers entity);

    BaseResponse update(AppUsers appUser, AppUsers entity);

    BaseResponse delete(AppUsers appUser, AppUsers appUserToDelete);

}