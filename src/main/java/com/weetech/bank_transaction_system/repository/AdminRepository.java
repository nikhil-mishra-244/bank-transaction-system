package com.weetech.bank_transaction_system.repository;

import com.weetech.bank_transaction_system.dto.AdminDashboardDTO;
import com.weetech.bank_transaction_system.entity.Transactions;
import com.weetech.bank_transaction_system.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AdminRepository extends JpaRepository<Transactions, Long> {

    @Query("""
            SELECT new com.weetech.bank_transaction_system.dto.AdminDashboardDTO(
            
                :totalUsers,
            
                COUNT(t.id),
            
                COALESCE(
                    SUM(
                        CASE
                            WHEN t.status = :success
                            THEN t.amount
                            ELSE 0
                        END
                    ),
                    0
                )
            
            )
            FROM Transactions t
            WHERE t.deleted = false
            AND (
                :fromDate IS NULL
                OR t.transactionDate >= :fromDate
            )
            AND (
                :toDate IS NULL
                OR t.transactionDate < :toDate
            )
            """)
    AdminDashboardDTO getAdminDashboard(

            @Param("totalUsers") Long totalUsers,

            @Param("fromDate") LocalDateTime fromDate,

            @Param("toDate") LocalDateTime toDate,

            @Param("success") TransactionStatus success);
}