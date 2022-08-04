/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.user.repositories;

import com.journal.florist.backend.feature.user.enums.ERole;
import com.journal.florist.backend.feature.user.model.AppRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<AppRoles, Long> {

    Optional<AppRoles> findByRoleName(ERole role);

}
