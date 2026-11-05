package com.weetech.bank_transaction_system.dto;

import com.weetech.bank_transaction_system.enums.TransactionTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExternalTransactionDTO {

    private String externalId;

    private BigDecimal amount;

    private TransactionTypes transactionType;

    private String description;

    private LocalDateTime transactionDate;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionTypes getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypes transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}