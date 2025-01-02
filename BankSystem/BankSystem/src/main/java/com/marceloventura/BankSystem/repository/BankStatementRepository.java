package com.marceloventura.BankSystem.repository;

import com.marceloventura.BankSystem.entity.BankStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankStatementRepository extends JpaRepository <BankStatement, Long> {
}
