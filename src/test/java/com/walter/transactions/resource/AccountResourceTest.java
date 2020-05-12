package com.walter.transactions.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.walter.transactions.model.Account;
import com.walter.transactions.utils.Constants;
import com.walter.transactions.utils.Json;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

/**
 * 
 * @author Walter Pedro
 * This class is responsible for the accounts integrated tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountResourceTest {
	
    @Autowired
    private MockMvc mvc;
    
    Account account;
    
    @Test
    public void mustInsertNewAccount()
      throws Exception {
     
        account = new Account();
        
        account.setDocumentNumber(Constants.VALID_CPF);
        account.setLimit(new BigDecimal(Constants.ACCOUNT_LIMIT));
     
        mvc.perform(post("/accounts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(Json.convertObjectToJson(account)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.documentNumber").value(Constants.VALID_CPF));
    }
    
    @Test
    public void mustNotInsertAccountWithExistentDocumentNumber()
      throws Exception {
     
        account = new Account();
        
        account.setDocumentNumber(Constants.VALID_CPF);
        account.setLimit(new BigDecimal(Constants.ACCOUNT_LIMIT));
        
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.convertObjectToJson(account)));
     
        mvc.perform(post("/accounts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(Json.convertObjectToJson(account)))
          .andExpect(status().isBadRequest());
    }
    
    @Test
    public void mustSearchAccountById()
      throws Exception {
     
        account = new Account();
        
        account.setDocumentNumber(Constants.VALID_CPF);
        account.setLimit(new BigDecimal(Constants.ACCOUNT_LIMIT));
        
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.convertObjectToJson(account)));
     
        mvc.perform(get("/accounts/{id}", 1)
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.documentNumber").value(Constants.VALID_CPF));
    }
    
    
}
