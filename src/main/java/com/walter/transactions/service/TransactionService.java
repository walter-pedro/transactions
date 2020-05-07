package com.walter.transactions.service;

import javax.naming.directory.InvalidAttributesException;

import com.walter.transactions.model.Transaction;

/**
 * @author Walter Pedro
 * Interface to transaction related services
 */
public interface TransactionService {
	public void save(Transaction transaction) throws InvalidAttributesException;
}
