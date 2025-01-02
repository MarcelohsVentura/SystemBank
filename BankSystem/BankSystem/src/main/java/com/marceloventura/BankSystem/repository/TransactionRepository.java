package com.marceloventura.BankSystem.repository;

import com.marceloventura.BankSystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository <Transaction, Long> {
}
