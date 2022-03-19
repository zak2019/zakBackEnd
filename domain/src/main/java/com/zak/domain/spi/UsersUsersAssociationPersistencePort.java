package com.zak.domain.spi;

import com.zak.domain.model.Account;
import com.zak.domain.model.User;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.domain.model.util.PageGenerics;

import java.util.Set;

public interface UsersUsersAssociationPersistencePort {

    UsersUsersAssociation createAssociation(UsersUsersAssociation association);
    UsersUsersAssociation update(UsersUsersAssociation association);
    UsersUsersAssociation findByInvitedUserAndInviter(User invitedUser, User inviter);
    UsersUsersAssociation findByInvitedUserAndInviterAndAccount(User invitedUser, User inviter, Account account);
    UsersUsersAssociation findByInvitedUserAndAccount(String userId, String accountId);
    UsersUsersAssociation findByAssociationId(String associationId);
    Set<UsersUsersAssociation> findByInviter(User user);
    Set<UsersUsersAssociation> findUserRolesByUserId(String userId);
    Set<UsersUsersAssociation> getListOfInvitedUsersByAccountIdByInviter(String accountId, String inviterId);
    Set<UsersUsersAssociation> getListOfInvitedUsersByAccountId(String accountId);
    PageGenerics<UsersUsersAssociation> getPageOfInvitedUsersByAccountIdByInviter(String accountId,
                                                                                  String str, int page,
                                                                                  int size, String sortBy);
    Set<UsersUsersAssociation> findByInvitedUser(User user);
    Boolean existsByInvitedUserAndInviter(User invitedUser, User inviter);
    Boolean delete(UsersUsersAssociation usersUsersAssociation);
}
