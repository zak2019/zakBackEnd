package com.zak.domain.spi;

import com.zak.domain.model.User;

import java.util.Optional;

public interface UserPersistencePort {

    Optional<User> createUser(User user);
    Optional<User> updateUser(User user);
    Optional<User> inviteUser(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findUserByUserId(String userId);
    Optional<User> findUserByUserSecretId(String secretId);
    Optional<User> findUserByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    String getConnectedUserId();
    String getConnectedUserSecretId();
}
