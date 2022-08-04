package com.journal.florist.app.security;

import com.journal.florist.backend.feature.user.model.AppUsers;

@FunctionalInterface
public interface CurrentUser {
    AppUsers getUser();
}
