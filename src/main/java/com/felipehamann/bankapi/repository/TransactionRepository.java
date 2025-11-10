package com.felipehamann.bankapi.repository;

import com.felipehamann.bankapi.model.BankAccount;
import com.felipehamann.bankapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(BankAccount account);
}
