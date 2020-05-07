package com.walter.transactions.repository;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.walter.transactions.model.OperationType;
import com.walter.transactions.repository.helper.OperationTypeRepositoryTestHelper;

/**
 * 
 * @author Walter Pedro
 * This class tests the OpetationTypeRepository class using HSQLDB
 */

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class OperationTypeRepositoryTest {
	
	@Autowired
	OperationTypeRepository operationTypeRepository;
	
	OperationType operationType;
	OperationTypeRepositoryTestHelper operationTypeRepositoryHelper;
	
	
	@Before
	public void setUp() {
		operationTypeRepositoryHelper = new OperationTypeRepositoryTestHelper(operationTypeRepository);
		operationTypeRepositoryHelper.insertOperationTypes();
		operationType = new OperationType();
	}
		
	@Test
	public void mustSearchOperationTypeWithValidId() {
		operationType = operationTypeRepositoryHelper.insertPaymentOperationType();
		Optional<OperationType> searchedOperation = 
				operationTypeRepository.findById(operationType.getOperationTypeId());
		
		assertTrue(searchedOperation.isPresent());
	}
	
	@Test
	public void mustNotSearchOperationTypeWithInvalidId() {
		Optional<OperationType> searchedOperation = 
				operationTypeRepository.findById(9430L);
		
		assertTrue(searchedOperation.isEmpty());
	}
	
}
