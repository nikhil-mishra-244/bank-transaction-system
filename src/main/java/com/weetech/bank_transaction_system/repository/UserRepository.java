package com.weetech.bank_transaction_system.repository;

import com.weetech.bank_transaction_system.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {

    boolean existsByEmail(String email);

}
