package com.zak.persistence.jpa.repository;

import com.zak.domain.enums.EUserRole;
import com.zak.persistence.jpa.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByName(EUserRole name);
}
