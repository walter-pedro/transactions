package com.walter.transactions.repository;

import org.springframework.data.repository.CrudRepository;

import com.walter.transactions.model.Transaction;


public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
