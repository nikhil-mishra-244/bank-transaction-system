
package com.weetech.bank_transaction_system.dto;

import com.weetech.bank_transaction_system.enums.TransactionStatus;
import com.weetech.bank_transaction_system.enums.TransactionTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {

    private Long id;
    private TransactionTypes transactionType;
    private BigDecimal amount;
    private String description;
    private TransactionStatus status;
    private LocalDateTime transactionDate;

    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(
            Long id,
            TransactionTypes transactionType,
            BigDecimal amount,
            String description,
            TransactionStatus status,
            LocalDateTime transactionDate
    ) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionTypes getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypes transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
