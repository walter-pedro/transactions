package com.walter.transactions.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.walter.transactions.model.Account;
import com.walter.transactions.model.OperationType;
import com.walter.transactions.model.Transaction;
import com.walter.transactions.repository.helper.AccountRepositoryTestHelper;
import com.walter.transactions.repository.helper.OperationTypeRepositoryTestHelper;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * 
 * @author Walter Pedro
 * This class tests the TransactionRepository class using HSQLDB
 */

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class TransactionRepositoryTest {
	
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	OperationTypeRepository operationTypeRepository;
	
	AccountRepositoryTestHelper accountRepositoryTestHelper;
	OperationTypeRepositoryTestHelper operationTypeRepositoryTestHelper;
	
	Transaction transaction;
	Account account;
	OperationType operationType;
	BigDecimal transactionAmount;
	Date eventDate;
	
	@Before
	public void setUp() {
		accountRepositoryTestHelper = new AccountRepositoryTestHelper(accountRepository);
		operationTypeRepositoryTestHelper = new OperationTypeRepositoryTestHelper(operationTypeRepository);
		
		transaction = new Transaction();
		transactionAmount = new BigDecimal(1000);
		eventDate = new Date();
		
		account = accountRepositoryTestHelper.insertAccount();
		operationType = operationTypeRepositoryTestHelper.insertInstallmentPurchaseOperationType();
		
		transaction.setAccount(account);
		transaction.setOperationType(operationType);
		transaction.setAmount(transactionAmount);
		transaction.setEventDate(eventDate);
	}
	
	@Test
	public void mustInsertTransactionWithValidAccountAndOperationType() {
		Transaction insertedTransaction = transactionRepository.save(transaction);
	
		assertThat(insertedTransaction.getAccount().getAccountId(), 
				is(account.getAccountId()));
		assertThat(insertedTransaction.getOperationType().getOperationTypeId(), 
				is(operationType.getOperationTypeId()));
		assertThat(insertedTransaction.getAmount(), is(transactionAmount));
		assertThat(insertedTransaction.getEventDate(), is(eventDate));
	}
	
}
