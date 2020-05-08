package com.walter.transactions.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.stereotype.Component;

import com.walter.transactions.model.Account;
import com.walter.transactions.model.OperationType;
import com.walter.transactions.model.Transaction;
import com.walter.transactions.repository.AccountRepository;
import com.walter.transactions.repository.OperationTypeRepository;
import com.walter.transactions.repository.TransactionRepository;
import com.walter.transactions.service.TransactionService;

import utils.Constants;

@Component
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
	public Transaction save(Transaction transaction) throws InvalidAttributesException {
		
		// Validate if exists the referenced account
		Optional<Account> account = accountRepository.findById(transaction.getAccount().getAccountId());
		if (account.isEmpty()) {
			throw new InvalidAttributesException("A conta informada nao existe");
		}
		
		// Validate if exists the referenced operation type and determine if the amount is positive or negative
		Optional<OperationType> operationType = operationTypeRepository.findById(transaction.getOperationType().getOperationTypeId());
		if (operationType.isEmpty()) {
			throw new InvalidAttributesException("O tipo de operação informado nao existe");
		} else if (!operationType.get().getOperationType().contentEquals(Constants.CREDIT_OPERATION_TYPE)) {
			transaction.setAmount(transaction.getAmount().multiply(new BigDecimal(-1)));
		}
		
		// Validate the account limit
		if ( account.get().getLimit().add( transaction.getAmount() )
				.compareTo(new BigDecimal(0) ) < 0) {
			throw new InvalidAttributesException("O valor da transacao e maior que o limite da conta");
		} else {
			account.get().setLimit(account.get().getLimit().add( transaction.getAmount()));
			accountRepository.save(account.get());
		}
		
		return transactionRepository.save(transaction);
		
	}
}
