package com.marceloventura.BankSystem.repository;

import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
