package com.zak.domain.spi;

import com.zak.domain.model.Account;

import java.util.Set;

public interface AccountPersistencePort {

    Account createAccount(Account account);
    Account getAccount(String accountId);
    Set<Account> getUsersAssociatedAccount(String userId);
}
