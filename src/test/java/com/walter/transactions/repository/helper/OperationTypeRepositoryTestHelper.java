package com.walter.transactions.repository.helper;

import com.walter.transactions.model.OperationType;
import com.walter.transactions.repository.OperationTypeRepository;

/**
 * 
 * @author Walter Pedro
 * This class supports the OperationTypeRepository related tests
 */
public class OperationTypeRepositoryTestHelper {
	
	private OperationTypeRepository operationTypeRepository;
	private OperationType operationType;
	
	public OperationTypeRepositoryTestHelper(OperationTypeRepository operationTypeRepository) {
		this.operationTypeRepository = operationTypeRepository;
	}
	
	public void insertOperationTypes() {
		insertPurchaseOperationType();
		insertInstallmentPurchaseOperationType();
		insertWithdrawOperationType();
		insertPaymentOperationType();
	}
	
	public OperationType insertPurchaseOperationType() {
		operationType = new OperationType();
		operationType.setDescription("COMPRA A VISTA");
		
		return operationTypeRepository.save(operationType);
	}
	
	public OperationType insertInstallmentPurchaseOperationType() {
		operationType = new OperationType();
		operationType.setDescription("COMPRA PARCELADA");
		
		return operationTypeRepository.save(operationType);
	}
	
	public OperationType insertWithdrawOperationType() {
		operationType = new OperationType();
		operationType.setDescription("SAQUE");
		
		return operationTypeRepository.save(operationType);
	}
	
	public OperationType insertPaymentOperationType() {
		operationType = new OperationType();
		operationType.setDescription("PAGAMENTO");
		
		return operationTypeRepository.save(operationType);
	}

}
