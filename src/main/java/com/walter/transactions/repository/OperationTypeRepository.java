package com.walter.transactions.repository;

import com.walter.transactions.model.OperationType;
import org.springframework.data.repository.CrudRepository;

public interface OperationTypeRepository extends CrudRepository<OperationType, Long> {
}
