package com.walter.transactions.service;

import com.walter.transactions.model.Account;

import javax.naming.directory.InvalidAttributesException;

/**
 * @author Walter Pedro
 * Interface to account related services
 */
public interface AccountService {
    public Account getAccountById(Long accountId) throws InvalidAttributesException;
	public void saveAccount(Account account) throws IllegalArgumentException, InvalidAttributesException;
}
