package com.weetech.bank_transaction_system.controller;

import com.weetech.bank_transaction_system.dto.TransactionImportRequestDTO;
import com.weetech.bank_transaction_system.dto.TransactionRequestDTO;
import com.weetech.bank_transaction_system.dto.TransactionResponseDTO;
import com.weetech.bank_transaction_system.dto.TransactionSummaryDTO;
import com.weetech.bank_transaction_system.enums.TransactionStatus;
import com.weetech.bank_transaction_system.enums.TransactionTypes;
import com.weetech.bank_transaction_system.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.weetech.bank_transaction_system.specification.UserPrincipal;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Create Transaction
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(Authentication authentication, @Valid @RequestBody TransactionRequestDTO requestDTO) {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        Long userId = principal.getUserId();

        TransactionResponseDTO response = transactionService.createTransaction(userId, requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get All / Filtered Transactions
    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions(Authentication authentication, @RequestParam(required = false) TransactionTypes type, @RequestParam(required = false) TransactionStatus status, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,

                                                                           @PageableDefault(page = 0, size = 10, sort = "transactionDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Long userId = getUserId(authentication);

        return ResponseEntity.ok(transactionService.filterTransactions(userId, type, status, startDate, endDate, pageable));
    }

    // Delete Transaction
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(Authentication authentication, @PathVariable Long transactionId) {

        Long userId = getUserId(authentication);

        transactionService.deleteTransaction(userId, transactionId);

        return ResponseEntity.noContent().build();
    }

    // Transaction Summary
    @GetMapping("/summary")
    public ResponseEntity<TransactionSummaryDTO> getTransactionSummary(Authentication authentication) {

        Long userId = getUserId(authentication);

        return ResponseEntity.ok(transactionService.getTransactionSummary(userId));
    }

    // Get Balance
    @GetMapping("/balance")
    public ResponseEntity<Map<String, BigDecimal>> getBalance(Authentication authentication) {

        Long userId = getUserId(authentication);

        return ResponseEntity.ok(Map.of("balance", transactionService.getBalance(userId)));
    }

    // Extract Logged-in User ID
    private Long getUserId(Authentication authentication) {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        return principal.getUserId();
    }


    // Import External Transactions
    @PostMapping("/import")
    public ResponseEntity<String> importTransactions(Authentication authentication, @Valid @RequestBody TransactionImportRequestDTO requestDTO) {

        Long userId = getUserId(authentication);

        transactionService.importTransactions(userId, requestDTO.getUrl());

        return ResponseEntity.ok("Transactions imported successfully");
    }

}
