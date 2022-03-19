package com.zak.persistence.jpa.adapter;

import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.User;
import com.zak.domain.spi.UserPersistencePort;
import com.zak.persistence.jpa.entity.UserEntity;
import com.zak.persistence.jpa.repository.UserRepository;
import com.zak.persistence.jpa.security.UserDetailsImpl;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class UserAdapter implements UserPersistencePort {

    private final UserRepository userRepository;
    private final MapperFacade orikaMapperFacade;


    public UserAdapter(UserRepository userRepository,
                       MapperFacade orikaMapperFacade) {
        this.userRepository = userRepository;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @Override

    public Optional<User> createUser(User user) {
        UserEntity userEntity = orikaMapperFacade.map(user, UserEntity.class);
        UserEntity newUser = userRepository.save(userEntity);
        return Optional.of(orikaMapperFacade.map(newUser, User.class));
    }

    @Override
    public Optional<User> updateUser(User user) {
        Optional<User> userByUserId = findUserByUserId(user.getUserId());
        user.setId(userByUserId.get().getId());
        user.setPassword(userByUserId.get().getPassword());
        UserEntity savedUser = userRepository.save(orikaMapperFacade.map(user, UserEntity.class));
        return Optional.of(orikaMapperFacade.map(savedUser, User.class));
    }

    @Override
    public Optional<User> inviteUser(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (!userEntity.isPresent()) {
            throw new UserNotFoundException(String.format("User with username %s not found", username));
        }
        return Optional.of(orikaMapperFacade.map(userEntity.get(), User.class));
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);
        if (!userEntity.isPresent()) {
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }
        return Optional.of(orikaMapperFacade.map(userEntity.get(), User.class));
    }

    @Override
    public Optional<User> findUserByUserSecretId(String secretId) {
        Optional<UserEntity> userEntity = userRepository.findBySecretId(secretId);
        if (!userEntity.isPresent()) {
            throw new UserNotFoundException(String.format("User with id %s not found", secretId));
        }
        return Optional.of(orikaMapperFacade.map(userEntity.get(), User.class));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmailIgnoreCase(email);
        if (!userEntity.isPresent()) {
            throw new UserNotFoundException();
        }
        return Optional.of(orikaMapperFacade.map(userEntity.get(), User.class));
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String getConnectedUserId() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUserId();
    }

    @Override
    public String getConnectedUserSecretId() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getSecretId();
    }
}
