package com.zak.application.service.api;

import com.zak.domain.model.Account;
import com.zak.domain.model.User;
import com.zak.domain.model.UsersUsersAssociation;

import java.util.Set;

public interface AccountService {

    UsersUsersAssociation createAccount(Account account, User user);
    Set<Account> getUsersAssociatedAccount(String userId);
    Account getAccoutByAccountId(String accountId);
}
