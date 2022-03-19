package com.zak.domain.spi;

import com.zak.domain.enums.EUserRole;
import com.zak.domain.model.UserRole;

import java.util.Optional;

public interface UserRolePersistencePort {
    Optional<UserRole> findByName(EUserRole name);
}
