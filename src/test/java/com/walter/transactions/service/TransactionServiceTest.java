package com.walter.transactions.service;

import com.walter.transactions.model.Account;
import com.walter.transactions.model.OperationType;
import com.walter.transactions.model.Transaction;
import com.walter.transactions.repository.AccountRepository;
import com.walter.transactions.repository.OperationTypeRepository;
import com.walter.transactions.repository.TransactionRepository;
import com.walter.transactions.service.impl.TransactionServiceImpl;
import com.walter.transactions.utils.Constants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    
    @Mock
    private AccountRepository accountRepository;
    
    @Mock
    private OperationTypeRepository operationTypeRepository;
    
    private TransactionService transactionService;
    
    private Transaction transaction;
    private Account account;
    private Account existentAccount;
    private OperationType operationType;
    private OperationType existentOperationType;

    @Before
    public void setUp() {
    	transactionService = new TransactionServiceImpl(transactionRepository, accountRepository, operationTypeRepository);
    	
    	transaction = new Transaction();
    	account = new Account();
    	operationType = new OperationType();
    	
    	existentAccount = new Account();
        existentAccount.setAccountId(Constants.VALID_ACCOUNT_ID);
        existentAccount.setDocumentNumber(Constants.VALID_CPF);
        
        existentOperationType = new OperationType();
        existentOperationType.setOperationTypeId(Constants.VALID_OPERATION_TYPE_ID);
    	
    	when(accountRepository.findById(Constants.VALID_ACCOUNT_ID)).thenReturn(Optional.of(existentAccount));
    	when(accountRepository.findById(Constants.INVALID_ACCOUNT_ID)).thenReturn(Optional.empty());
    	
    	when(operationTypeRepository.findById(Constants.VALID_OPERATION_TYPE_ID)).thenReturn(Optional.of(existentOperationType));
    	when(operationTypeRepository.findById(Constants.INVALID_OPERATION_TYPE_ID)).thenReturn(Optional.empty());
    }

    @Test
    public void mustInsertNewTransaction() throws InvalidAttributesException {
    	account.setAccountId(Constants.VALID_ACCOUNT_ID);
    	operationType.setOperationTypeId(Constants.VALID_OPERATION_TYPE_ID);
    	
    	transaction.setAccount(account);
    	transaction.setOperationType(operationType);
    	transaction.setAmount(new BigDecimal(1000));
    	transaction.setEventDate(new Date());
    	
    	transactionService.save(transaction);
    	
    	verify(transactionRepository).save(transaction);
    }

    @Test(expected = InvalidAttributesException.class)
    public void mustNotInsertTransactionWithInvalidOperationType() throws InvalidAttributesException {
    	account.setAccountId(Constants.VALID_ACCOUNT_ID);
    	operationType.setOperationTypeId(Constants.INVALID_OPERATION_TYPE_ID);
    	
    	transaction.setAccount(account);
    	transaction.setOperationType(operationType);
    	transaction.setAmount(new BigDecimal(1000));
    	transaction.setEventDate(new Date());
    	
    	transactionService.save(transaction);
    	
    	verify(transactionRepository).save(transaction);
    }

    @Test(expected = InvalidAttributesException.class)
    public void mustNotInsertTransactionWithNonexistentAccount() throws InvalidAttributesException {
    	account.setAccountId(Constants.INVALID_ACCOUNT_ID);
    	operationType.setOperationTypeId(Constants.VALID_OPERATION_TYPE_ID);
    	
    	transaction.setAccount(account);
    	transaction.setOperationType(operationType);
    	transaction.setAmount(new BigDecimal(1000));
    	transaction.setEventDate(new Date());
    	
    	transactionService.save(transaction);
    	
    	verify(transactionRepository).save(transaction);
    }

}
