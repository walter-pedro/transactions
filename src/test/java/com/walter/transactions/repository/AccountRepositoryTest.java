package com.walter.transactions.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.walter.transactions.model.Account;
import com.walter.transactions.repository.helper.AccountRepositoryTestHelper;
import com.walter.transactions.utils.Constants;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * 
 * @author Walter Pedro
 * This class tests the AccountRepository using HSQLDB
 */

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class AccountRepositoryTest {
	
	@Autowired
	private AccountRepository accountRepository;
	
	private AccountRepositoryTestHelper accountRepositoryTestHelper;
	private Account account;
	
	@Before
	public void setUp() {
		accountRepositoryTestHelper = new AccountRepositoryTestHelper(accountRepository);
		account = new Account();
	}
	
	@Test
	public void mustInsertNewAccount() {		
		Account savedAccount = accountRepositoryTestHelper.insertAccount(Constants.VALID_CPF);	
		assertThat(savedAccount.getDocumentNumber(), is(Constants.VALID_CPF));		
	}
	
	@Test
	public void mustSearchAccountById() {
		Account insertedAccount = accountRepositoryTestHelper.insertAccount(Constants.VALID_CPF);
		
		Optional<Account> searchAccount = accountRepository.findById(insertedAccount.getAccountId());		
		assertTrue(searchAccount.isPresent());
		
		account = searchAccount.get();
		assertThat(account.getDocumentNumber(), is(Constants.VALID_CPF));
	}
	
	@Test
	public void mustSearchAccountByDocumentNumber() {
		Account insertedAccount = accountRepositoryTestHelper.insertAccount();		
		
		Optional<Account> searchAccount = accountRepository.findByDocumentNumber(Constants.VALID_CPF);		
		assertTrue(searchAccount.isPresent());
		
		account = searchAccount.get();
		assertThat(account.getAccountId(), is(insertedAccount.getAccountId()));
	}
	
	@Test
	public void updateAccountLimit() {
		Account insertedAccount = accountRepositoryTestHelper.insertAccount();		
		
		BigDecimal newLimit = insertedAccount.getLimit().add(
				new BigDecimal(10));
		
		insertedAccount.setLimit(newLimit);
		
		insertedAccount = accountRepository.save(insertedAccount);
		
		assertThat(insertedAccount.getLimit(), is(newLimit));
	}
	
}
