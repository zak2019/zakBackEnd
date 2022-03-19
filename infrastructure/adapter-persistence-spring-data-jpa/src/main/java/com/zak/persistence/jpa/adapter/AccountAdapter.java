package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.Account;
import com.zak.domain.spi.AccountPersistencePort;
import com.zak.persistence.jpa.entity.AccountEntity;
import com.zak.persistence.jpa.repository.AccountRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class AccountAdapter implements AccountPersistencePort {

    private final AccountRepository accountRepository;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public AccountAdapter(AccountRepository accountRepository,
                          MapperFacade orikaMapperFacade) {
        this.accountRepository = accountRepository;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @Override
    public Account createAccount(Account account) {
        AccountEntity savedAccount =
                accountRepository.save(orikaMapperFacade.map(account, AccountEntity.class));
        return orikaMapperFacade.map(savedAccount, Account.class);
    }

    @Override
    public Account getAccount(String accountId) {
        return orikaMapperFacade.map(accountRepository.findByAccountId(accountId), Account.class);
    }

    @Override
    public Set<Account> getUsersAssociatedAccount(String userId) {
        return orikaMapperFacade.mapAsSet(accountRepository.getUsersAssociatedAccounts(userId), Account.class);
    }
}
