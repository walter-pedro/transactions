package com.walter.transactions.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Walter Pedro
 * This entuty Represents the OperationsType table
 */

@Entity
@Table(name = "OperationsTypes")
public class OperationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationType;

    @NotNull
    private String description;

    public Long getOperationType() {
        return operationType;
    }

    public void setOperationType(Long operationType) {
        this.operationType = operationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
