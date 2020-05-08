package com.walter.transactions.repository.helper;

import java.math.BigDecimal;

import com.walter.transactions.model.Account;
import com.walter.transactions.repository.AccountRepository;
import com.walter.transactions.utils.Constants;

/**
 * 
 * @author Walter Pedro
 * This class supports the AccountRepository related tests
 */
public class AccountRepositoryTestHelper {
	
	private AccountRepository accountRepository;
	private Account account;
	
	public AccountRepositoryTestHelper(AccountRepository accountRepository) {
		this.accountRepository= accountRepository; 
	}
	
	public Account insertAccount() {
		account = new Account();
		account.setDocumentNumber(Constants.VALID_CPF);
		account.setLimit(new BigDecimal(Constants.ACCOUNT_LIMIT));
		return accountRepository.save(account);
	}
	
	public Account insertAccount(String documentNumber) {
		account = new Account();
		account.setDocumentNumber(documentNumber);
		account.setLimit(new BigDecimal(Constants.ACCOUNT_LIMIT));
		return accountRepository.save(account);
	}
}
