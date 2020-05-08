package com.walter.transactions.resource;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walter.transactions.model.Transaction;
import com.walter.transactions.service.TransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Walter Pedro
 * This class exposes the transactions services
 */
@RestController
@RequestMapping("/transactions")
@Api(value="ProductsAPI")
public class TransactionResource {
	
	@Autowired
	TransactionService transactionService;
	
	@ApiOperation(value="Inserts a new transaction")
	@PostMapping
	public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) throws InvalidAttributesException {
		
		Transaction newTransaction = transactionService.save(transaction);
		
		return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
	}
	
	@ExceptionHandler({InvalidAttributesException.class})
	public ResponseEntity<Error> handle(IllegalArgumentException e) {
		return new ResponseEntity<>(new Error(e.getMessage()), 
				HttpStatus.BAD_REQUEST);
	}

}
