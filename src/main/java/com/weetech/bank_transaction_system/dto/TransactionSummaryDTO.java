package com.weetech.bank_transaction_system.dto;

import java.math.BigDecimal;

public class TransactionSummaryDTO {

    private BigDecimal totalCredit;
    private BigDecimal totalDebit;
    private BigDecimal currentBalance;

    private Long totalTransactions;
    private Long successfulTransactions;
    private Long failedTransactions;
    private Long pendingTransactions;

    public TransactionSummaryDTO(BigDecimal totalCredit, BigDecimal totalDebit, BigDecimal currentBalance, Long totalTransactions, Long successfulTransactions, Long failedTransactions, Long pendingTransactions) {

        this.totalCredit = totalCredit;
        this.totalDebit = totalDebit;
        this.currentBalance = currentBalance;
        this.totalTransactions = totalTransactions;
        this.successfulTransactions = successfulTransactions;
        this.failedTransactions = failedTransactions;
        this.pendingTransactions = pendingTransactions;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public Long getSuccessfulTransactions() {
        return successfulTransactions;
    }

    public Long getFailedTransactions() {
        return failedTransactions;
    }

    public Long getPendingTransactions() {
        return pendingTransactions;
    }
}