package com.weetech.bank_transaction_system.dto;

import com.weetech.bank_transaction_system.enums.Role;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserProfileResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Role role;
    private BigDecimal balance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private LocalDateTime createdDate;

    public UserProfileResponseDTO(
            Long id,
            String name,
            String email,
            Role role,
            BigDecimal balance,
            BigDecimal totalIncome,
            BigDecimal totalExpenses,
            LocalDateTime createdDate
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.balance = balance;
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}