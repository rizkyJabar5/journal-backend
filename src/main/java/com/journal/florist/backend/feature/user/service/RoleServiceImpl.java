package com.journal.florist.backend.feature.user.service;

import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.user.enums.ERole;
import com.journal.florist.backend.feature.user.model.AppRoles;
import com.journal.florist.backend.feature.user.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.journal.florist.app.constant.UserConstant.ROLE_IS_NOT_FOUND_MSG;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public AppRoles getRolesByName(ERole roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new NotFoundException(String.format(ROLE_IS_NOT_FOUND_MSG, roleName)));
    }

    @Override
    public List<AppRoles> getAllRoles() {
        return roleRepository.findAll();
    }


    @Override
    public AppRoles saveNewRole(AppRoles role) {
        return roleRepository.save(role);
    }


}
