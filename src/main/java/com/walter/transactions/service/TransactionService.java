package com.walter.transactions.service;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.stereotype.Component;

import com.walter.transactions.model.Transaction;

/**
 * @author Walter Pedro
 * Interface to transaction related services
 */
@Component
public interface TransactionService {
	public Transaction save(Transaction transaction) throws InvalidAttributesException;
}
