package com.zak.application.service.api;

import com.zak.domain.model.Account;
import com.zak.domain.model.User;
import com.zak.domain.model.UserRole;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.domain.model.util.PageGenerics;

import java.util.Set;

public interface UsersUsersAssociationService {

    UsersUsersAssociation createUserAccountAssociation(User user, Account account);
    UsersUsersAssociation createAssociation(UsersUsersAssociation association);
    UsersUsersAssociation updateAssociation(UsersUsersAssociation association);
    UsersUsersAssociation saveUsersAssociation(User connectedUser, User createdUser, Set<UserRole> roles, Account account);
    UsersUsersAssociation activateAssociation(User invitedUser, User inviter);
    UsersUsersAssociation findByAssociationId(String associationId);
    UsersUsersAssociation findByInvitedUserAndInviter(User user, User inviter);
    UsersUsersAssociation findByInvitedUserAndInviterAndAccount(User user, User inviter, Account account);
    UsersUsersAssociation findByInvitedUserIdAndAccountId(String userId, String accountId);
    Set<UsersUsersAssociation> findByInvitedUser(String userId);
    Set<UsersUsersAssociation> findByInviter(String idUser);
    Set<UsersUsersAssociation> getUserRolesByUserId(String idUser);
    Set<UsersUsersAssociation> getListOfInvitedUsersByAccountIdByInviter(String accountId, String inviterId);
    Set<UsersUsersAssociation> getListOfInvitedUsersByAccountId(String accountId);
    PageGenerics<UsersUsersAssociation> getPageOfInvitedUsersByAccountIdByInviter(String accountId,
                                                                                  String str, int page,
                                                                                  int size, String sortBy);
    UsersUsersAssociation acceptAssociation(String idAssociation, Boolean response);
    UsersUsersAssociation addRoleToUserAssociation(String associationId, String role);
    UsersUsersAssociation deleteRoleFromUserAssociation(String associationId, String role);
    Boolean existsByInvitedUserAndInviter(User user, User ainviter);
    Boolean deleteUserAssociation(String associationId);
}
