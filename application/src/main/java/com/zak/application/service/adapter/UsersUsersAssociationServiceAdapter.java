package com.zak.application.service.adapter;

import com.zak.application.service.api.UserRoleService;
import com.zak.application.service.api.UsersUsersAssociationService;
import com.zak.domain.enums.EUserRole;
import com.zak.domain.exception.UsersAssociationException;
import com.zak.domain.model.Account;
import com.zak.domain.model.User;
import com.zak.domain.model.UserRole;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.domain.model.util.PageGenerics;
import com.zak.domain.spi.IdGererator;
import com.zak.domain.spi.UserPersistencePort;
import com.zak.domain.spi.UsersUsersAssociationPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UsersUsersAssociationServiceAdapter implements UsersUsersAssociationService {

    @Autowired
    private IdGererator idGererator;

    private final UsersUsersAssociationPersistencePort usersAssociationPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final UserRoleService userRoleService;

    @Autowired
    public UsersUsersAssociationServiceAdapter(UsersUsersAssociationPersistencePort usersAssociationPersistencePort,
                                               UserPersistencePort userPersistencePort,
                                               UserRoleService userRoleService) {
        this.usersAssociationPersistencePort = usersAssociationPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.userRoleService = userRoleService;
    }

    @Override
    public UsersUsersAssociation createUserAccountAssociation(User user, Account account) {
        UserRole userRole = userRoleService.findByName(EUserRole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: role Admin not found."));
        Set<UserRole> roles = new HashSet<UserRole>();
        roles.add(userRole);
        UsersUsersAssociation usersUsersAssociation = new UsersUsersAssociation();
        usersUsersAssociation.setEnabled(false);
        usersUsersAssociation.setAssociationId(idGererator.generateUniqueId());
        usersUsersAssociation.setCreationDate(new Date());
        usersUsersAssociation.setAccount(account);
        usersUsersAssociation.setInviter(user);
        usersUsersAssociation.setInvitedUser(user);
        usersUsersAssociation.setRoles(roles);
        return createAssociation(usersUsersAssociation);
    }

    @Override
    public UsersUsersAssociation createAssociation(UsersUsersAssociation association) {
        return usersAssociationPersistencePort.createAssociation(association);
    }

    @Override
    public UsersUsersAssociation saveUsersAssociation(User connectedUser, User createdUser, Set<UserRole> roles, Account account) {
        UsersUsersAssociation usersUsersAssociation = new UsersUsersAssociation();
        usersUsersAssociation.setCreationDate(new Date());
        usersUsersAssociation.setInviter(connectedUser);
        usersUsersAssociation.setInvitedUser(createdUser);
        usersUsersAssociation.setAssociationId(idGererator.generateUniqueId());
        usersUsersAssociation.setEnabled(false);
        usersUsersAssociation.setRoles(roles);
        usersUsersAssociation.setAccount(account);
        return createAssociation(usersUsersAssociation);
    }

    @Override
    public UsersUsersAssociation activateAssociation(User invitedUser, User inviter) {
        UsersUsersAssociation association =
                usersAssociationPersistencePort.findByInvitedUserAndInviter(invitedUser, inviter);
        association.setEnabled(true);
        return usersAssociationPersistencePort.update(association);
    }

    @Override
    public UsersUsersAssociation updateAssociation(UsersUsersAssociation association) {
        return  usersAssociationPersistencePort.update(association);
    }

    @Override
    public UsersUsersAssociation findByAssociationId(String associationId) {
        return usersAssociationPersistencePort.findByAssociationId(associationId);
    }

    @Override
    public Set<UsersUsersAssociation> findByInviter(String idUser) {
        Optional<User> user = userPersistencePort.findUserByUserId(idUser);
        return usersAssociationPersistencePort.findByInviter(user.get());
    }

    @Override
    public Set<UsersUsersAssociation> getUserRolesByUserId(String userId) {
        return usersAssociationPersistencePort.findUserRolesByUserId(userId);
    }

    @Override
    public Set<UsersUsersAssociation> getListOfInvitedUsersByAccountIdByInviter(String accountId, String inviterId) {
        return usersAssociationPersistencePort.getListOfInvitedUsersByAccountIdByInviter(accountId, inviterId);
    }

    @Override
    public Set<UsersUsersAssociation> getListOfInvitedUsersByAccountId(String accountId) {
        return usersAssociationPersistencePort.getListOfInvitedUsersByAccountId(accountId);
    }

    @Override
    public PageGenerics<UsersUsersAssociation> getPageOfInvitedUsersByAccountIdByInviter(String accountId,
                                                                                         String str,
                                                                                         int page,
                                                                                         int size,
                                                                                         String sortBy) {
        return usersAssociationPersistencePort.getPageOfInvitedUsersByAccountIdByInviter(accountId, str, page, size, sortBy);
    }

    @Override
    public Set<UsersUsersAssociation> findByInvitedUser(String userId) {
        Optional<User> user = userPersistencePort.findUserByUserId(userId);
       return usersAssociationPersistencePort.findByInvitedUser(user.get());
    }

    @Override
    public UsersUsersAssociation findByInvitedUserAndInviter(User user, User inviter) {
        return usersAssociationPersistencePort.findByInvitedUserAndInviter(user, inviter);
    }

    @Override
    public UsersUsersAssociation findByInvitedUserAndInviterAndAccount(User user, User inviter, Account account) {
        return usersAssociationPersistencePort.findByInvitedUserAndInviterAndAccount(user, inviter, account);
    }

    @Override
    public UsersUsersAssociation findByInvitedUserIdAndAccountId(String userId, String accountId) {
        return usersAssociationPersistencePort.findByInvitedUserAndAccount(userId, accountId);
    }

    @Override
    public UsersUsersAssociation acceptAssociation(String associationId, Boolean response) {
        UsersUsersAssociation assoc = usersAssociationPersistencePort.findByAssociationId(associationId);
        assoc.setEnabled(response);
        return usersAssociationPersistencePort.update(assoc);
    }

    @Override
    public UsersUsersAssociation addRoleToUserAssociation(String associationId, String role) {
       UsersUsersAssociation oldData = usersAssociationPersistencePort.findByAssociationId(associationId);
        if (oldData == null) {
            throw new UsersAssociationException();
        }
        UserRole newRole = userRoleService.getRole(role);
        Set<UserRole> userRoles = oldData.getRoles();
        if (!userRoles.contains(newRole)) {
            userRoles.add(newRole);
            oldData.setRoles(userRoles);
        }

        return usersAssociationPersistencePort.update(oldData);
    }


    @Override
    public UsersUsersAssociation deleteRoleFromUserAssociation(String associationId, String role) {
        UsersUsersAssociation oldData = usersAssociationPersistencePort.findByAssociationId(associationId);
        if (oldData == null) {
            throw new UsersAssociationException();
        }
        UserRole roleToDelete = userRoleService.getRole(role);
        Set<UserRole> userRoles = oldData.getRoles();
        if (userRoles.contains(roleToDelete)) {
            userRoles.remove(roleToDelete);
            oldData.setRoles(userRoles);
        }

        return usersAssociationPersistencePort.update(oldData);
    }


    @Override
    public Boolean existsByInvitedUserAndInviter(User user, User inviter) {
        return usersAssociationPersistencePort.existsByInvitedUserAndInviter(user, inviter);
    }

    @Override
    public Boolean deleteUserAssociation(String associationId) {
        UsersUsersAssociation association =
                usersAssociationPersistencePort.findByAssociationId(associationId);
        if (association == null) {
            throw new UsersAssociationException();
        }
        return usersAssociationPersistencePort.delete(association);
    }
}
