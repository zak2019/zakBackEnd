package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.AccountService;
import com.zak.domain.model.Account;
import com.zak.infrastructure.rest.controller.resource.dto.AccountDto;
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private MapperFacade mapperFacade;
    private AccountController accountController;

    @Before
    public void setUp() throws Exception {
        accountController = new AccountController(accountService, mapperFacade);
    }

    @Test
    public void should_get_account_by_accoun_id(){
        // given
        String accountId = "id_account";
        Account account = new Account();
        account.setAccountId(accountId);
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountId(accountId);
        //when
        when(accountService.getAccoutByAccountId(accountId)).thenReturn(account);
        when(mapperFacade.map(any(), any())).thenReturn(accountDto);
        //then
        AccountDto acc = accountController.getAccount(accountId);
        assertEquals(acc, accountDto);
    }
}

