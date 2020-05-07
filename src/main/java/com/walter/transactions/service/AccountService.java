package com.walter.transactions.service;

import com.walter.transactions.model.Account;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author Walter Pedro
 * Interface to account related services
 */
@Component
public interface AccountService {
    public Account getAccountById(Long accountId) throws InvalidAttributesException;
	public Account saveAccount(Account account) throws IllegalArgumentException, InvalidAttributesException;
}
