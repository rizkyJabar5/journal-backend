/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.user.dto.AppUserBuilder;
import com.journal.florist.backend.feature.user.dto.RequestAppUser;
import com.journal.florist.backend.feature.user.dto.RequestUpdateAppUser;
import com.journal.florist.backend.feature.user.model.AppUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface AppUserService extends HasLogger {

    Page<AppUsers> findAnyMatching(Optional<String> filter, Pageable pageable);
    long countAnyMatching(Optional<String> filter);
    AppUsers findUserById(Long userId);
    AppUsers getByUserId(Long id);
    Page<AppUserBuilder> getAllUsers(Pageable pageable);
    AppUsers findByEmailOrUsername(String email, String username);

    //    ------------CRUD SERVICE--------------//
    BaseResponse save(RequestAppUser request);

    BaseResponse update(RequestUpdateAppUser request);

    SuccessResponse delete(Long userId);

}