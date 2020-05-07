package com.walter.transactions.resource;

import javax.naming.directory.InvalidAttributesException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walter.transactions.model.Account;
import com.walter.transactions.service.AccountService;

/*
 * @author Walter Pedro
 * This class exposes the account services
 */
@RestController
@RequestMapping(value="/accounts")
public class AccountResource {
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("/{accountId}")
	public ResponseEntity<Account> getAccountById(
			@PathVariable("accountId") Long accountId) throws InvalidAttributesException {
		
		Account account = accountService.getAccountById(accountId);
		
		return new ResponseEntity<>(account, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Account> saveAccount(@RequestBody @Valid Account account) throws IllegalArgumentException, InvalidAttributesException {
		
		Account newAccount = accountService.saveAccount(account);
		
		return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
	}
	
	@ExceptionHandler({InvalidAttributesException.class})
	public ResponseEntity<Error> handle(InvalidAttributesException e) {
		return new ResponseEntity<>(new Error(e.getMessage()), 
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<Error> handle(IllegalArgumentException e) {
		return new ResponseEntity<>(new Error(e.getMessage()), 
				HttpStatus.BAD_REQUEST);
	}
}
