package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.Account;
import com.zak.domain.model.User;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.domain.model.util.PageGenerics;
import com.zak.domain.spi.UsersUsersAssociationPersistencePort;
import com.zak.persistence.jpa.entity.AccountEntity;
import com.zak.persistence.jpa.entity.UserEntity;
import com.zak.persistence.jpa.entity.UsersUsersAssociationEntity;
import com.zak.persistence.jpa.repository.UsersUsersAssociationRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Set;

public class UsersUsersAssociationAdapter implements UsersUsersAssociationPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final UsersUsersAssociationRepository usersUsersAssociationRepository;

    @Autowired
    public UsersUsersAssociationAdapter(MapperFacade orikaMapperFacade,
                                        UsersUsersAssociationRepository usersUsersAssociationRepository) {
        this.orikaMapperFacade = orikaMapperFacade;
        this.usersUsersAssociationRepository = usersUsersAssociationRepository;
    }

    @Override
    public UsersUsersAssociation createAssociation(UsersUsersAssociation association) {
        UsersUsersAssociationEntity UUAssociation =
                orikaMapperFacade.map(association, UsersUsersAssociationEntity.class);
        UsersUsersAssociationEntity saved = usersUsersAssociationRepository.save(UUAssociation);
        return orikaMapperFacade.map(saved, UsersUsersAssociation.class);
    }

    @Override
    public UsersUsersAssociation update(UsersUsersAssociation association) {
        UsersUsersAssociationEntity UUAssociation =
                orikaMapperFacade.map(association, UsersUsersAssociationEntity.class);
        UsersUsersAssociationEntity saved = usersUsersAssociationRepository.save(UUAssociation);
        return orikaMapperFacade.map(saved, UsersUsersAssociation.class);
    }

    @Override
    public UsersUsersAssociation findByInvitedUserAndInviter(User invitedUser, User inviter) {
        UserEntity user = orikaMapperFacade.map(invitedUser, UserEntity.class);
        UserEntity invitedBy = orikaMapperFacade.map(inviter, UserEntity.class);

        return orikaMapperFacade.map(
                usersUsersAssociationRepository.findByInvitedUserAndInviter(user, invitedBy),
                UsersUsersAssociation.class
        );
    }

    @Override
    public UsersUsersAssociation findByInvitedUserAndInviterAndAccount(User invitedUser, User inviter, Account account) {
        UserEntity user = orikaMapperFacade.map(invitedUser, UserEntity.class);
        UserEntity invitedBy = orikaMapperFacade.map(inviter, UserEntity.class);
        AccountEntity accountEntity = orikaMapperFacade.map(account, AccountEntity.class);

        return orikaMapperFacade.map(
                usersUsersAssociationRepository.findByInvitedUserAndInviterAndAccount(user, invitedBy, accountEntity),
                UsersUsersAssociation.class
        );
    }

    @Override
    public UsersUsersAssociation findByInvitedUserAndAccount(String userId, String accountId) {

     return  orikaMapperFacade.map(
                usersUsersAssociationRepository.findByInvitedUserIdAndAccountId(userId, accountId),
                UsersUsersAssociation.class);
    }

    @Override
    public UsersUsersAssociation findByAssociationId(String associationId) {
        return orikaMapperFacade.map(
                usersUsersAssociationRepository.findByAssociationId(associationId),
                UsersUsersAssociation.class
        );
    }

    @Override
    public Set<UsersUsersAssociation> findByInviter(User user) {
        UserEntity userEntity = orikaMapperFacade.map(user, UserEntity.class);
        Set<UsersUsersAssociationEntity> usersSet = usersUsersAssociationRepository.findByInviter(userEntity);
        return orikaMapperFacade.mapAsSet(usersSet, UsersUsersAssociation.class);
    }

    @Override
    public Set<UsersUsersAssociation> findUserRolesByUserId(String userId) {
        Set<UsersUsersAssociationEntity> usersSet = usersUsersAssociationRepository.getUserRolesByUserId(userId);
        return orikaMapperFacade.mapAsSet(usersSet, UsersUsersAssociation.class);
    }

    @Override
    public Set<UsersUsersAssociation> getListOfInvitedUsersByAccountIdByInviter(String accountId, String inviterId) {
        Set<UsersUsersAssociationEntity> usersSet =
                usersUsersAssociationRepository.getListOfInvitedUsersByAccountIdByInviter(accountId, inviterId);
        return orikaMapperFacade.mapAsSet(usersSet, UsersUsersAssociation.class);
    }

    @Override
    public Set<UsersUsersAssociation> getListOfInvitedUsersByAccountId(String accountId) {
        Set<UsersUsersAssociationEntity> usersSet =
                usersUsersAssociationRepository.getListOfInvitedUsersByAccountId(accountId);
        return orikaMapperFacade.mapAsSet(usersSet, UsersUsersAssociation.class);
    }

    @Override
    public PageGenerics<UsersUsersAssociation> getPageOfInvitedUsersByAccountIdByInviter(String accountId,
                                                                                         String str,
                                                                                         int page, int size,
                                                                                         String sortBy) {
        Page<UsersUsersAssociationEntity> pageOfInvitedUsersByInviter =
                usersUsersAssociationRepository
                        .getPageOfInvitedUsersByAccountIdByInviter(
                                accountId,
                                str,
                                PageRequest.of(page, size, Sort.by(sortBy)));

        PageGenerics<UsersUsersAssociation> pageOfInvitedUsers = new PageGenerics<>();

        pageOfInvitedUsers
                .setData(
                        orikaMapperFacade.mapAsSet(
                                pageOfInvitedUsersByInviter.getContent(),
                        UsersUsersAssociation.class));
        pageOfInvitedUsers.setSize(pageOfInvitedUsersByInviter.getNumberOfElements());
        pageOfInvitedUsers.setActualPage(pageOfInvitedUsersByInviter.getNumber());
        pageOfInvitedUsers.setTotalPages(pageOfInvitedUsersByInviter.getTotalPages());
        pageOfInvitedUsers.setTotalData((int) pageOfInvitedUsersByInviter.getTotalElements());
        return pageOfInvitedUsers;
    }

    @Override
    public Set<UsersUsersAssociation> findByInvitedUser(User user) {
        return orikaMapperFacade.mapAsSet(
                usersUsersAssociationRepository.findByInvitedUser(orikaMapperFacade.map(user, UserEntity.class)),
                        UsersUsersAssociation.class);
    }

    @Override
    public Boolean existsByInvitedUserAndInviter(User invitedUser, User inviter) {
        return usersUsersAssociationRepository.existsByInvitedUserAndInviter(invitedUser, inviter);
    }

    @Override
    public Boolean delete(UsersUsersAssociation usersUsersAssociation) {
        usersUsersAssociationRepository.delete(
                orikaMapperFacade.map(usersUsersAssociation,
                        UsersUsersAssociationEntity.class));
        return true;
    }
}
