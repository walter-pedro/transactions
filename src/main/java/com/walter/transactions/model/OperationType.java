package com.walter.transactions.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Walter Pedro
 * This entity Represents the OperationsType table
 */

@Entity
@Table(name = "OperationsTypes")
public class OperationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationTypeId;

    @NotNull
    private String description;

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
