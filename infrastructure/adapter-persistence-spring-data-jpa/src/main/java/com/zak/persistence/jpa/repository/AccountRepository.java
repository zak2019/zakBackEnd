package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.AccountEntity;
import com.zak.persistence.jpa.entity.UsersUsersAssociationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    AccountEntity findByAccountId(String accountId);

    @Query("select DISTINCT u.account" +
            " from UsersUsersAssociationEntity u" +
            " where u.invitedUser.userId = :userId")
    Set<AccountEntity> getUsersAssociatedAccounts(@Param("userId") String userId);
}
