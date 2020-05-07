package com.walter.transactions.service;

import com.walter.transactions.model.Account;
import com.walter.transactions.repository.AccountRepository;
import com.walter.transactions.service.impl.AccountServiceImpl;
import com.walter.transactions.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.directory.InvalidAttributesException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Walter Pedro
 * This class tests the AccountService class
 */
@RunWith(SpringRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService accountService;

    private Account account;
    private Account existentAccount;

    @Before
    public void init() {
        accountService = new AccountServiceImpl(accountRepository);
        account = new Account();
        
        existentAccount = new Account();
        existentAccount.setAccountId(Constants.VALID_ACCOUNT_ID);
        existentAccount.setDocumentNumber(Constants.VALID_CPF);
    }

    @Test
    public void mustInsertNewAccount() throws IllegalArgumentException, InvalidAttributesException {
    	account.setDocumentNumber(Constants.VALID_CPF);
        accountService.saveAccount(account);
        verify(accountRepository).save(account);
    }

    @Test
    public void mustSearchAccountById() throws InvalidAttributesException {       
        when(accountRepository.findById(Constants.VALID_ACCOUNT_ID)).thenReturn(Optional.of(existentAccount));
        account = accountService.getAccountById(Constants.VALID_ACCOUNT_ID);
        assertThat(account.getAccountId(), is(existentAccount.getAccountId()));
    }

    @Test(expected = InvalidAttributesException.class)
    public void mustValidateIfDocumentAlreadyExists() throws IllegalArgumentException, InvalidAttributesException {    	
    	when(accountRepository.findByDocumentNumber(Constants.VALID_CPF)).thenReturn(Optional.of(existentAccount));    	
    	account.setDocumentNumber(Constants.VALID_CPF);
        accountService.saveAccount(account);
    	
    }

    @Test(expected = InvalidAttributesException.class)
    public void mustReturnErrorWithNonexistentAccountId() throws InvalidAttributesException {    	
    	when(accountRepository.findById(Constants.VALID_ACCOUNT_ID)).thenReturn(Optional.empty());
        account = accountService.getAccountById(Constants.VALID_ACCOUNT_ID);
        assertThat(account.getAccountId(), is(existentAccount.getAccountId()));        
    }

}
