package com.walter.transactions.model;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Walter Pedro
 * This entity represents the Accounts table
 */

@Entity
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotNull
    @Length(min = 11, max = 14)
    private String documentNumber;
    
    @NotNull
    private BigDecimal limit;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
}
