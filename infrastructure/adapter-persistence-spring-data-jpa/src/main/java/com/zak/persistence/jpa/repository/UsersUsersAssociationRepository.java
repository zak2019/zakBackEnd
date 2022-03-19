package com.zak.persistence.jpa.repository;

import com.zak.domain.model.User;
import com.zak.persistence.jpa.entity.AccountEntity;
import com.zak.persistence.jpa.entity.UserEntity;
import com.zak.persistence.jpa.entity.UsersUsersAssociationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UsersUsersAssociationRepository extends JpaRepository<UsersUsersAssociationEntity, Long> {

    Set<UsersUsersAssociationEntity> findByInviter(UserEntity user);

    @Query("select new UsersUsersAssociationEntity(u.associationId, u.invitedUser, u.creationDate, u.isEnabled)" +
            " from UsersUsersAssociationEntity u" +
            " where u.inviter.userId = :inviterId" +
            " and u.account.accountId = :accountId")
    Set<UsersUsersAssociationEntity> getListOfInvitedUsersByAccountIdByInviter(@Param("accountId") String accountId,
                                                                               @Param("inviterId") String inviterId);

    @Query("select u" +
            " from UsersUsersAssociationEntity u" +
            " where u.account.accountId = :accountId")
    Set<UsersUsersAssociationEntity> getListOfInvitedUsersByAccountId(@Param("accountId") String accountId);

    @Query("select u" +
            " from UsersUsersAssociationEntity u" +
            " where u.invitedUser.userId = :userId")
    Set<UsersUsersAssociationEntity> getUserRolesByUserId(@Param("userId") String userId);

    //    not working due to u.roles
//    @Query("select new UsersUsersAssociationEntity(u.associationId, u.invitedUser, u.creationDate, u.isEnabled, u.roles)" +
//            " from UsersUsersAssociationEntity u" +
//            " where u.inviter.userId = :inviterId" +
//            " and lower(u.invitedUser.username) LIKE %:str%")
    // select only invited users
//    @Query("select u" +
//            " from UsersUsersAssociationEntity u" +
//            " where u.inviter.userId = :inviterId" +
//            " and u.invitedUser.userId != :inviterId" +
//            " and u.account.accountId = :accountId" +
//            " and lower(u.invitedUser.username) LIKE %:str%")

    // select invited users + the inviter
    @Query("select u" +
            " from UsersUsersAssociationEntity u" +
            " where u.account.accountId = :accountId" +
            " and lower(u.invitedUser.username) LIKE %:str%")
    Page<UsersUsersAssociationEntity> getPageOfInvitedUsersByAccountIdByInviter(
            @Param("accountId") String accountId,
            @Param("str") String str,
            Pageable pageable);

    Set<UsersUsersAssociationEntity> findByInvitedUser(UserEntity user);

    UsersUsersAssociationEntity findByInvitedUserAndInviter(UserEntity userInvited, UserEntity inviter);

    @Query("select u" +
            " from UsersUsersAssociationEntity u" +
            " where u.invitedUser.userId = :userId" +
            " and u.account.accountId = :accountId")
    UsersUsersAssociationEntity findByInvitedUserIdAndAccountId(@Param("userId") String userId,
                                                                @Param("accountId") String accountId);

    UsersUsersAssociationEntity findByInvitedUserAndInviterAndAccount(UserEntity userInvited,
                                                                      UserEntity inviter,
                                                                      AccountEntity account);

    UsersUsersAssociationEntity findByAssociationId(String associationId);

    // not working ??
    Boolean existsByInvitedUserAndInviter(User invitedUser, User inviter);
}
