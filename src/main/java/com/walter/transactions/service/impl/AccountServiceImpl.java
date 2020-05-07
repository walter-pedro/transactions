package com.walter.transactions.service.impl;

import com.walter.transactions.model.Account;
import com.walter.transactions.repository.AccountRepository;
import com.walter.transactions.service.AccountService;
import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Walter Pedro
 * THis class implements the services for accounts functionality
 */
@Component
public class AccountServiceImpl implements AccountService {

	@Autowired
    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
    
    public AccountServiceImpl() {
		
	}
    
    @Override
    public Account getAccountById(Long accountId) throws InvalidAttributesException {
        return accountRepository.findById(accountId).orElseThrow(InvalidAttributesException::new);
    }
    
    @Override
    public Account saveAccount(Account account) throws IllegalArgumentException, InvalidAttributesException  {
    	
    	if (!accountRepository.findByDocumentNumber(account.getDocumentNumber()).isEmpty()) {
    		throw new InvalidAttributesException("O CPF informado ja esta cadastrado na base.");
    	}
    	
    	return accountRepository.save(account);
    }
}
