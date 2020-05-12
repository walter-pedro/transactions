package com.walter.transactions.repository;

import com.walter.transactions.model.OperationType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface OperationTypeRepository extends CrudRepository<OperationType, Long> {
	public Optional<List<OperationType>> findByOperationType(String operationType);
}
