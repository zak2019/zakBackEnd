package com.zak.application.service.adapter;

import com.zak.application.service.api.UserRoleService;
import com.zak.domain.enums.EUserRole;
import com.zak.domain.model.UserRole;
import com.zak.domain.spi.UserRolePersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserRoleServiceAdapter implements UserRoleService {

    private UserRolePersistencePort userRolePersistencePort;

    @Autowired
    public UserRoleServiceAdapter(UserRolePersistencePort userRolePersistencePort) {
        this.userRolePersistencePort = userRolePersistencePort;
    }

    @Override
    public Optional<UserRole> findByName(EUserRole name) {
        return userRolePersistencePort.findByName(name);
    }

    @Override
    public UserRole getRole(String role) {
        switch (role) {
            case "mod":
                UserRole userRoleMod = userRolePersistencePort.findByName(EUserRole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role moderator is not found."));
                return userRoleMod;
            case "admin":
                UserRole userRoleAdmin = userRolePersistencePort.findByName(EUserRole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role admin is not found."));
                return userRoleAdmin;
            default:
                UserRole userRole = userRolePersistencePort.findByName(EUserRole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Default role is not found."));
                return userRole;
        }
    }
}
