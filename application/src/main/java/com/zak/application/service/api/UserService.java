package com.zak.application.service.api;

import com.zak.domain.model.User;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.domain.model.util.ResponseContent;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> createUser(User user);
    Optional<User> persistNewUser(User user);
    Optional<User> updateUser(User user);
    Optional<User> completeUserAccountCreation(String idAssociation, User user);
    Optional<User> addRoleToUser(String idUser, String role);
    Optional<User> deleteRoleFromUser(String idUser, String role);
    List<ResponseContent<UsersUsersAssociation>> inviteUsers(String emails, String accountId);
    String getInviteUsersResponseMessage(List<UsersUsersAssociation> associations, String emails);
    Optional<User> findByUsername(String username);
    Optional<User> findUserByUserId(String userId);
    Optional<User> findUserByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    String getConnectedUserId();
}
