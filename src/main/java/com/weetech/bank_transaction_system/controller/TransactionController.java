package com.weetech.bank_transaction_system.controller;

import com.weetech.bank_transaction_system.dto.TransactionRequestDTO;
import com.weetech.bank_transaction_system.dto.TransactionResponseDTO;
import com.weetech.bank_transaction_system.dto.TransactionSummaryDTO;
import com.weetech.bank_transaction_system.entity.Transactions;
import com.weetech.bank_transaction_system.enums.TransactionStatus;
import com.weetech.bank_transaction_system.enums.TransactionTypes;
import com.weetech.bank_transaction_system.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    // Create new transaction
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@PathVariable Long userId, @Valid @RequestBody TransactionRequestDTO requestDTO) {

        TransactionResponseDTO response = transactionService.createTransaction(userId, requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // Get all transactions of a user
//    @GetMapping
//    public ResponseEntity<List<TransactionResponseDTO>> fetchAllTransactions(@PathVariable Long userId) {
//        return ResponseEntity.ok(transactionService.fetchAllTransactions(userId));
//    }


    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllOrFilteredTransactions(

            @PathVariable Long userId,
            @RequestParam(required = false) TransactionTypes type,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<TransactionResponseDTO> transactions = transactionService.filterTransactions(userId, type, status, startDate, endDate);

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/balance")
    public ResponseEntity<TransactionSummaryDTO> getTransactionSummary( @PathVariable Long userId) {
        return ResponseEntity.ok(
                transactionService.getTransactionSummary(userId)
        );
    }

}
