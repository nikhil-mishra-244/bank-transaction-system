package com.weetech.bank_transaction_system.specification;

import com.weetech.bank_transaction_system.entity.Transactions;
import com.weetech.bank_transaction_system.enums.TransactionStatus;
import com.weetech.bank_transaction_system.enums.TransactionTypes;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TransactionSpecification {

    public static Specification<Transactions> hasUserId(Long userId) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Transactions> hasTransactionType(
            TransactionTypes type) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("transactionType"), type);
    }

    public static Specification<Transactions> hasStatus(
            TransactionStatus status) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Transactions> hasStartDate(
            LocalDateTime startDate) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("transactionDate"), startDate);
    }

    public static Specification<Transactions> hasEndDate(
            LocalDateTime endDate) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(
                        root.get("transactionDate"), endDate);
    }

    public static Specification<Transactions> isNotDeleted() {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isFalse(root.get("deleted"));
    }
}