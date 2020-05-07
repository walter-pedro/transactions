package com.walter.transactions.service.impl;

import com.walter.transactions.model.Account;
import com.walter.transactions.repository.AccountRepository;
import com.walter.transactions.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.directory.InvalidAttributesException;

/**
 * 
 * @author Walter Pedro
 * Tgis class implements the services for accounts functionality
 */
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
    
    @Override
    public Account getAccountById(Long accountId) throws InvalidAttributesException {
        return accountRepository.findById(accountId).orElseThrow(InvalidAttributesException::new);
    }
    
    @Override
    public void saveAccount(Account account) throws IllegalArgumentException, InvalidAttributesException  {
    	
    	if (!accountRepository.findByDocumentNumber(account.getDocumentNumber()).isEmpty()) {
    		throw new InvalidAttributesException("O CPF informado ja esta cadastrado na base.");
    	}
    	
    	accountRepository.save(account);
    }
}
