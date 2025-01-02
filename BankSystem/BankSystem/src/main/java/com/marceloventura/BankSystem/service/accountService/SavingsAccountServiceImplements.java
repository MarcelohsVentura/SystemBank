package com.marceloventura.BankSystem.service.accountService;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;
import com.marceloventura.BankSystem.entity.SavingsAccount;
import com.marceloventura.BankSystem.repository.AccountRepository;
import com.marceloventura.BankSystem.util.validator.Validator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class SavingsAccountServiceImplements implements SavingsAccountService {

    private final Validator validator;

    private final AccountRepository accountRepository;

    @Override
    public double savingsAccountMonthlyIncome (TransactionRequestDTO transactionRequestDTO, double amount) {
        SavingsAccount savingsAccount = (SavingsAccount) accountRepository.findById(transactionRequestDTO.getId())
                        .orElseThrow(()-> new EntityNotFoundException("Account not found"));

        amount = transactionRequestDTO.getAmount();

        validator.validateBalanceType(transactionRequestDTO, savingsAccount.getBalance(), amount);

        double interest = (amount > 200) ? amount * savingsAccount.getMonthlyIncome() : 0;
        double totalAmount = savingsAccount.getBalance() + amount + interest;

        savingsAccount.setBalance(totalAmount);
        accountRepository.save(savingsAccount);

        return savingsAccount.getBalance();
    }
}
