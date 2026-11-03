
package com.weetech.bank_transaction_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.weetech.bank_transaction_system.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(
            min = 3,
            message = "Name Should have atleast 3 Characters"
    )
    private String name;

    @Email(message = "Please provide a valid email")
    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Account Balance
    @Getter
    @Setter
    @Column(precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    // Total Income
    @Column(precision = 15, scale = 2)
    private BigDecimal totalIncome = BigDecimal.ZERO;

    // Total Expenses
    @Column(precision = 15, scale = 2)
    private BigDecimal totalExpenses = BigDecimal.ZERO;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Transactions> transactions = new ArrayList<>();

    private LocalDateTime createdDate;

    public Users() {
    }

    public Users(
            String name,
            String email,
            String password,
            Role role,
            BigDecimal balance
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.balance = balance != null ? balance : BigDecimal.ZERO;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
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

    public String getPassword() {
        return password;
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

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void addTransaction(Transactions transaction) {
        transactions.add(transaction);
        transaction.setUser(this);
    }
}
