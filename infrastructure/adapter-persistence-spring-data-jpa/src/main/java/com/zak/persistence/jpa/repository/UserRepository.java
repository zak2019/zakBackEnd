package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findBySecretId(String secretId);

    Optional<UserEntity> findByEmailIgnoreCase(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
