package com.zak.application.service.adapter;

import com.zak.application.service.api.AccountService;
import com.zak.application.service.api.ConfirmationTokenService;
import com.zak.application.service.api.UserService;
import com.zak.application.service.api.UsersUsersAssociationService;
import com.zak.domain.exception.AccountException;
import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.Account;
import com.zak.domain.model.ConfirmationToken;
import com.zak.domain.model.User;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.domain.spi.AccountPersistencePort;
import com.zak.domain.spi.IdGererator;
import com.zak.infrastructure.provider.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class AccountServiceAdapter implements AccountService {

    @Autowired
    private IdGererator idGererator;

    public final AccountPersistencePort accountPersistencePort;
    public final UserService userService;
    public final UsersUsersAssociationService usersUsersAssociationService;
    public final ConfirmationTokenService confirmationTokenService;
    public final EmailService emailService;

    @Autowired
    public AccountServiceAdapter(AccountPersistencePort accountPersistencePort,
                                 UserService userService,
                                 UsersUsersAssociationService usersUsersAssociationService,
                                 ConfirmationTokenService confirmationTokenService,
                                 EmailService emailService) {
        this.accountPersistencePort = accountPersistencePort;
        this.userService = userService;
        this.usersUsersAssociationService = usersUsersAssociationService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }


    @Override
    @Transactional
    public UsersUsersAssociation createAccount(Account account, User user) {
        account.setAccountId(idGererator.generateUniqueId());
        account.setCreationDate(new Date());
        Account savedAccount = accountPersistencePort.createAccount(account);
        if (savedAccount == null) {
            throw new AccountException("Error creating account");
        }

        Optional<User> savedNewUser = userService.persistNewUser(user);
        if (!savedNewUser.isPresent()) {
            throw new UserNotFoundException("Error creating user account");
        }




        UsersUsersAssociation userAccountAssociation =
                usersUsersAssociationService.createUserAccountAssociation(savedNewUser.get(), savedAccount);
        User inviter = userAccountAssociation.getInviter();
        if (userAccountAssociation != null
                && inviter != null
                && userAccountAssociation.getAccount() != null) {
            Optional<ConfirmationToken> confirmationToken =
                    confirmationTokenService.createAndAddConfirmationToken(inviter);

            emailService.sendEmail(
                    inviter.getEmail(),
                    "Complete Registration!",
                    "<h3>Welcome To ZAK " + inviter.getUsername() +
                            "</h1><p>To confirm your account, please click or copy this link : " +
                            "http://localhost:4200/user-verification/" +
                            userAccountAssociation.getAssociationId()
                            + "/" + confirmationToken.get().getConfirmationToken() + "</p>"
            );
        }
        return userAccountAssociation;
    }

    @Override
    public Set<Account> getUsersAssociatedAccount(String userId) {
        return accountPersistencePort.getUsersAssociatedAccount(userId);
    }

    @Override
    public Account getAccoutByAccountId(String accountId) {
        return accountPersistencePort.getAccount(accountId);
    }
}
