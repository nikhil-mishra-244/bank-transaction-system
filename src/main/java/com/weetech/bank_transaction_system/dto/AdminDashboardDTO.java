package com.weetech.bank_transaction_system.dto;

import java.math.BigDecimal;

public class AdminDashboardDTO {

    private Long totalUsers;
    private Long totalTransactions;
    private BigDecimal totalTransactionAmount;

    public AdminDashboardDTO(
            Long totalUsers,
            Long totalTransactions,
            BigDecimal totalTransactionAmount) {

        this.totalUsers = totalUsers;
        this.totalTransactions = totalTransactions;
        this.totalTransactionAmount = totalTransactionAmount;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public BigDecimal getTotalTransactionAmount() {
        return totalTransactionAmount;
    }
}