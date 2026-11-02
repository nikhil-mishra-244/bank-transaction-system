package com.weetech.bank_transaction_system.service;

import com.weetech.bank_transaction_system.dto.TransactionRequestDTO;
import com.weetech.bank_transaction_system.dto.TransactionResponseDTO;
import com.weetech.bank_transaction_system.dto.TransactionSummaryDTO;
import com.weetech.bank_transaction_system.entity.Transactions;
import com.weetech.bank_transaction_system.entity.Users;
import com.weetech.bank_transaction_system.enums.TransactionStatus;
import com.weetech.bank_transaction_system.enums.TransactionTypes;
import com.weetech.bank_transaction_system.repository.TransactionRepository;
import com.weetech.bank_transaction_system.repository.UserRepository;
import com.weetech.bank_transaction_system.specification.TransactionSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }


    // Convert Entity to DTO
    private TransactionResponseDTO convertToDTO(Transactions transaction) {

        return new TransactionResponseDTO(transaction.getId(), transaction.getTransactionType(), transaction.getAmount(), transaction.getDescription(), transaction.getStatus(), transaction.getTransactionDate());
    }


    //    Create a Transaction
    public TransactionResponseDTO createTransaction(Long userId, TransactionRequestDTO requestDTO) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal amount = requestDTO.getAmount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid transaction amount");
        }

        if (requestDTO.getTransactionType() == null) {
            throw new RuntimeException("Transaction type is required");
        }

        Transactions transaction = new Transactions();

        transaction.setTransactionType(requestDTO.getTransactionType());
        transaction.setAmount(amount);
        transaction.setDescription(requestDTO.getDescription());
        transaction.setStatus(TransactionStatus.PENDING);

        user.addTransaction(transaction);

        try {

            if (transaction.getTransactionType() == TransactionTypes.CREDIT) {

                user.setBalance(user.getBalance().add(amount));
                user.setTotalIncome(user.getTotalIncome().add(amount));

            } else if (transaction.getTransactionType() == TransactionTypes.DEBIT) {

                if (user.getBalance().compareTo(amount) < 0) {
                    throw new RuntimeException("Insufficient balance");
                }
                user.setBalance(user.getBalance().subtract(amount));
                user.setTotalExpenses(user.getTotalExpenses().add(amount));
            }

            transaction.setStatus(TransactionStatus.SUCCESS);

        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setDescription(e.getMessage());
        }

        userRepository.save(user);
        Transactions savedTransaction = transactionRepository.save(transaction);
        return convertToDTO(savedTransaction);
    }


    //  Get All Transactions Of User
    public List<TransactionResponseDTO> fetchAllTransactions(long id) {

        Optional<Users> user = userRepository.findById(id);
        if (user.isEmpty()) throw new RuntimeException("User Not Found ID : " + id);

        return user.get().getTransactions().stream().map(this::convertToDTO).toList();
    }


    // Filter Transactions
    public List<TransactionResponseDTO> filterTransactions(Long userId, TransactionTypes type, TransactionStatus status, LocalDateTime startDate, LocalDateTime endDate) {

        // Check user exists
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Validate date range
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date cannot be after end date");
        }

        // Base specification
        Specification<Transactions> specification = (root, query, cb) -> cb.conjunction();

        // Always filter by user
        specification = specification.and(TransactionSpecification.hasUserId(userId));

        // Exclude deleted transactions
        specification = specification.and(TransactionSpecification.isNotDeleted());

        // Filter by transaction type
        if (type != null) {
            specification = specification.and(TransactionSpecification.hasTransactionType(type));
        }

        // Filter by status
        if (status != null) {
            specification = specification.and(TransactionSpecification.hasStatus(status));
        }

        // Filter by start date
        if (startDate != null) {
            specification = specification.and(TransactionSpecification.hasStartDate(startDate));
        }

        // Filter by end date
        if (endDate != null) {
            specification = specification.and(TransactionSpecification.hasEndDate(endDate));
        }

        // Fetch filtered transactions
        List<Transactions> transactions = transactionRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "transactionDate"));

        // Convert Entity to DTO
        return transactions.stream().map(this::convertToDTO).toList();
    }


    // Get User Balance
    public TransactionSummaryDTO getTransactionSummary(Long userId){

        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));

        List<Transactions> transactions = user.getTransactions().stream().filter(transaction -> !transaction.isDeleted()).toList();

//        BigDecimal totalCredit = transactions.stream()
//                .filter(t -> t.getTransactionType() == TransactionTypes.CREDIT)
//                .filter(t -> t.getStatus() == TransactionStatus.SUCCESS)
//                .map(Transactions::getAmount)
//                .reduce(BigDecimal.ZERO,BigDecimal::add);
//
//        BigDecimal totalDebit = transactions.stream()
//                .filter(t -> t.getTransactionType() == TransactionTypes.DEBIT)
//                .filter(t -> t.getStatus() == TransactionStatus.SUCCESS)
//                .map(Transactions::getAmount)
//                .reduce(BigDecimal.ZERO,BigDecimal::add);

        long successful = transactions.stream()
                .filter(t -> t.getStatus() == TransactionStatus.SUCCESS)
                .count();

        long failed = transactions.stream()
                .filter(t -> t.getStatus() == TransactionStatus.FAILED)
                .count();

        long pending = transactions.stream()
                .filter(t -> t.getStatus() == TransactionStatus.PENDING)
                .count();

        return new TransactionSummaryDTO(
                user.getTotalIncome(),
                user.getTotalExpenses(),
                user.getBalance(),
                (long) transactions.size(),
                successful,
                failed,
                pending
        );


    }


}
