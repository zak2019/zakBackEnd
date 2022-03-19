package com.zak.application.service.api;

import com.zak.domain.enums.EUserRole;
import com.zak.domain.model.UserRole;

import java.util.Optional;

public interface UserRoleService {
    Optional<UserRole> findByName(EUserRole name);
    UserRole getRole(String role);
}
