package com.journal.florist.backend.feature.user.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.user.enums.ERole;
import com.journal.florist.backend.feature.user.model.AppRoles;

import java.util.List;

public interface RoleService extends HasLogger {

    AppRoles getRolesByName(ERole roleName);

    List<AppRoles> getAllRoles();

    AppRoles saveNewRole(AppRoles role);

}
