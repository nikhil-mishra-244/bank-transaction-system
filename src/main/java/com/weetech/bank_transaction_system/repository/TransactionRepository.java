package com.weetech.bank_transaction_system.repository;

import com.weetech.bank_transaction_system.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transactions, Long>, JpaSpecificationExecutor<Transactions> {

    List<Transactions> findByUserIdAndDeletedFalse(Long userId);

    Optional<Transactions> findByIdAndUserIdAndDeletedFalse(Long id, Long userId);

}
