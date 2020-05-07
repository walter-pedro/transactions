package com.walter.transactions.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import com.walter.transactions.model.OperationType;
import com.walter.transactions.model.Transaction;
import com.walter.transactions.repository.AccountRepository;
import com.walter.transactions.repository.OperationTypeRepository;
import com.walter.transactions.repository.TransactionRepository;
import com.walter.transactions.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {

	TransactionRepository transactionRepository;
	AccountRepository accountRepository;
	OperationTypeRepository operationTypeRepository;
	
	public TransactionServiceImpl(TransactionRepository transactionRepository,
			AccountRepository accountRepository, OperationTypeRepository operationTypeRepository) {
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
		this.operationTypeRepository = operationTypeRepository;
	}
	
	@Override
	public void save(Transaction transaction) throws InvalidAttributesException {
		
		// Validate if exists the referenced account
		if (accountRepository.findById(transaction.getAccount().getAccountId()).isEmpty()) {
			throw new InvalidAttributesException("A conta informada nao existe");
		}
		
		// Validate if exists the referenced operation type and determine if the amount is positive or negative
		Optional<OperationType> operationType = operationTypeRepository.findById(transaction.getOperationType().getOperationTypeId());
		if (operationType.isEmpty()) {
			throw new InvalidAttributesException("O tipo de operação informado nao existe");
		} else if (operationType.get().getOperationTypeId() != 4) {
			transaction.setAmount(transaction.getAmount().multiply(new BigDecimal(-1)));
		}		
		
		transactionRepository.save(transaction);
		
	}
}
