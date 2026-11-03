
package com.weetech.bank_transaction_system.controller;

import com.weetech.bank_transaction_system.dto.TransactionRequestDTO;
import com.weetech.bank_transaction_system.dto.TransactionResponseDTO;
import com.weetech.bank_transaction_system.dto.TransactionSummaryDTO;
import com.weetech.bank_transaction_system.entity.Transactions;
import com.weetech.bank_transaction_system.enums.TransactionStatus;
import com.weetech.bank_transaction_system.enums.TransactionTypes;
import com.weetech.bank_transaction_system.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Create new transaction
    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @PathVariable Long userId,
            @Valid @RequestBody TransactionRequestDTO requestDTO
    ) {
        TransactionResponseDTO response =
                transactionService.createTransaction(userId, requestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get All Transactions
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getAllOrFilteredTransactions(
            @PathVariable Long userId,
            @RequestParam(required = false) TransactionTypes type,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate
    ) {
        List<TransactionResponseDTO> transactions =
                transactionService.filterTransactions(
                        userId, type, status, startDate, endDate
                );

        return ResponseEntity.ok(transactions);
    }

    // Transaction Delete
    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<String> deleteTransaction(
            @PathVariable Long userId,
            @PathVariable Long transactionId
    ) {
        transactionService.deleteTransaction(userId, transactionId);

        return ResponseEntity.ok("Transaction deleted successfully");
    }

    // Get User Balance Summary
    @GetMapping("/transactions/summary")
    public ResponseEntity<TransactionSummaryDTO> getTransactionSummary(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                transactionService.getTransactionSummary(userId)
        );
    }

    // Get User Balance Summary
    @GetMapping("/balance")
    public ResponseEntity<Map<String, BigDecimal>> getBalance(
            @PathVariable Long userId
    ) {
        BigDecimal balance = transactionService.getBalance(userId);

        return ResponseEntity.ok(
                Map.of("balance", balance)
        );
    }
}
