package com.weetech.bank_transaction_system.service;

import com.weetech.bank_transaction_system.dto.AdminDashboardDTO;
import com.weetech.bank_transaction_system.enums.TransactionStatus;
import com.weetech.bank_transaction_system.repository.AdminRepository;
import com.weetech.bank_transaction_system.repository.TransactionRepository;
import com.weetech.bank_transaction_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AdminService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public AdminService(TransactionRepository transactionRepository, UserRepository userRepository, AdminRepository adminRepository) {

        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }


    //    Admin dashboard Data
    public AdminDashboardDTO getDashboard(LocalDate fromDate, LocalDate toDate) {

        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("fromDate cannot be after toDate");
        }

        Long totalUsers = userRepository.count();

        LocalDateTime startDate = fromDate == null ? null : fromDate.atStartOfDay();

        LocalDateTime endDate = toDate == null ? null : toDate.plusDays(1).atStartOfDay();

        return adminRepository.getAdminDashboard(totalUsers, startDate, endDate, TransactionStatus.SUCCESS);
    }
}