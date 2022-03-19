package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.AccountService;
import com.zak.domain.model.Account;
import com.zak.infrastructure.rest.controller.resource.dto.AccountDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final MapperFacade orikaMapperFacade;
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService,
                             MapperFacade orikaMapperFacade) {
        this.accountService = accountService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @GetMapping("/{accountId}")
    public AccountDto getAccount(@PathVariable final String accountId) {
        Account account = accountService.getAccoutByAccountId(accountId);
        return orikaMapperFacade.map(account, AccountDto.class);
    }
}
