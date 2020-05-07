package com.walter.transactions.repository;

import com.walter.transactions.model.Account;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
	Optional<Account> findByDocumentNumber(String documentNumber);
}
