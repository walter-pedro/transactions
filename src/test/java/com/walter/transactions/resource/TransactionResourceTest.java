package com.walter.transactions.resource;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.math.BigDecimal;
import java.util.Date;

import javax.naming.directory.InvalidAttributesException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.walter.transactions.model.Account;
import com.walter.transactions.model.Transaction;
import com.walter.transactions.repository.AccountRepository;
import com.walter.transactions.repository.OperationTypeRepository;
import com.walter.transactions.utils.Constants;
import com.walter.transactions.utils.Json;

/**
 * 
 * @author Walter Pedro
 * This class is responsible for the transactions integrated tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionResourceTest {

    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private OperationTypeRepository operationTypeRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    Transaction transaction;
    Account account;
    
    @Before
    public void setUp() {
    	account = new Account();
    	transaction = new Transaction();
    }
    
    @Test
    public void mustInsertNewPurchaseTransaction() throws Exception {
    	account.setDocumentNumber(Constants.VALID_CPF);
        account.setLimit(new BigDecimal(Constants.ACCOUNT_LIMIT));
     
        MvcResult result = mvc.perform(post("/accounts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(Json.convertObjectToJson(account)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.documentNumber").value(Constants.VALID_CPF))
          .andReturn();
        
        JsonNode jsonNode = Json.convertJsonToObject(result.getResponse().getContentAsString());
        
        account.setAccountId(jsonNode.findValue("accountId").asLong());
        
        transaction.setAccount(account);
        transaction.setOperationType(operationTypeRepository.findByOperationType(Constants.DEBIT_OPERATION_TYPE).get().get(0));
        transaction.setAmount(new BigDecimal(Constants.ACCOUNT_LIMIT));
        transaction.setEventDate(new Date());
        
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.convertObjectToJson(transaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(Constants.ACCOUNT_LIMIT * -1));
        
        account = accountRepository.findByDocumentNumber(Constants.VALID_CPF).get();
        assertTrue(account.getLimit().compareTo(BigDecimal.ZERO) == 0);        
    }
    
    @Test 
    public void mustInsertNewPaymentTransaction() throws Exception {
    	account.setDocumentNumber(Constants.VALID_CPF);
        account.setLimit(new BigDecimal(Constants.ACCOUNT_LIMIT));
     
        MvcResult result = mvc.perform(post("/accounts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(Json.convertObjectToJson(account)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.documentNumber").value(Constants.VALID_CPF))
          .andReturn();
        
        JsonNode jsonNode = Json.convertJsonToObject(result.getResponse().getContentAsString());
        
        account.setAccountId(jsonNode.findValue("accountId").asLong());
        
        transaction.setAccount(account);
        transaction.setOperationType(operationTypeRepository.findByOperationType(Constants.CREDIT_OPERATION_TYPE).get().get(0));
        transaction.setAmount(new BigDecimal(Constants.ACCOUNT_LIMIT));
        transaction.setEventDate(new Date());
        
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.convertObjectToJson(transaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(Constants.ACCOUNT_LIMIT));
        
        account = accountRepository.findByDocumentNumber(Constants.VALID_CPF).get();
        assertTrue(account.getLimit().compareTo(new BigDecimal(Constants.ACCOUNT_LIMIT * 2)) == 0);
    }
    
    @Test
    public void mustNotInsertPurchaseAboveTheLimit() throws Exception {
    	account.setDocumentNumber(Constants.VALID_CPF);
        account.setLimit(new BigDecimal(Constants.ACCOUNT_LIMIT));
     
        MvcResult result = mvc.perform(post("/accounts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(Json.convertObjectToJson(account)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.documentNumber").value(Constants.VALID_CPF))
          .andReturn();
        
        JsonNode jsonNode = Json.convertJsonToObject(result.getResponse().getContentAsString());
        
        account.setAccountId(jsonNode.findValue("accountId").asLong());
        
        transaction.setAccount(account);
        transaction.setOperationType(operationTypeRepository.findByOperationType(Constants.DEBIT_OPERATION_TYPE).get().get(0));
        transaction.setAmount(new BigDecimal(Constants.ACCOUNT_LIMIT + 10));
        transaction.setEventDate(new Date());
        
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.convertObjectToJson(transaction)))
                .andExpect(status().isBadRequest())
                .andExpect(resultTransaction -> assertTrue(resultTransaction.getResolvedException() instanceof InvalidAttributesException));
    }
    
    @Test
    public void mustNotInsertTransactionWithInvalidAccount() throws Exception {
    	account.setDocumentNumber(Constants.VALID_CPF);
        account.setLimit(new BigDecimal(Constants.ACCOUNT_LIMIT));
     
        MvcResult result = mvc.perform(post("/accounts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(Json.convertObjectToJson(account)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.documentNumber").value(Constants.VALID_CPF))
          .andReturn();
        
        JsonNode jsonNode = Json.convertJsonToObject(result.getResponse().getContentAsString());
        
        account.setAccountId(jsonNode.findValue("accountId").asLong() + 1);
        
        transaction.setAccount(account);
        transaction.setOperationType(operationTypeRepository.findByOperationType(Constants.DEBIT_OPERATION_TYPE).get().get(0));
        transaction.setAmount(new BigDecimal(Constants.ACCOUNT_LIMIT));
        transaction.setEventDate(new Date());
        
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.convertObjectToJson(transaction)))
                .andExpect(status().isBadRequest())
                .andExpect(resultTransaction -> assertTrue(resultTransaction.getResolvedException() instanceof InvalidAttributesException));
    }
    
    @Test
    public void mustNotInsertTransactionWithInvalidOperation() throws Exception {
    	account.setDocumentNumber(Constants.VALID_CPF);
        account.setLimit(new BigDecimal(Constants.ACCOUNT_LIMIT));
     
        MvcResult result = mvc.perform(post("/accounts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(Json.convertObjectToJson(account)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.documentNumber").value(Constants.VALID_CPF))
          .andReturn();
        
        JsonNode jsonNode = Json.convertJsonToObject(result.getResponse().getContentAsString());
        
        account.setAccountId(jsonNode.findValue("accountId").asLong());
        
        transaction.setAccount(account);
        transaction.setOperationType(operationTypeRepository.findByOperationType(Constants.DEBIT_OPERATION_TYPE).get().get(0));
        transaction.setAmount(new BigDecimal(Constants.ACCOUNT_LIMIT));
        transaction.setEventDate(new Date());
        
        transaction.getOperationType().setOperationTypeId(Constants.INVALID_OPERATION_TYPE_ID);
        
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.convertObjectToJson(transaction)))
                .andExpect(status().isBadRequest())
                .andExpect(resultTransaction -> assertTrue(resultTransaction.getResolvedException() instanceof InvalidAttributesException));
    }    
    
}
