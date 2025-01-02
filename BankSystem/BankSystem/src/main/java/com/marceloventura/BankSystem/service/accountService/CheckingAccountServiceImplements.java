package com.marceloventura.BankSystem.service.accountService;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;
import com.marceloventura.BankSystem.entity.CheckingAccount;
import com.marceloventura.BankSystem.repository.AccountRepository;
import com.marceloventura.BankSystem.util.validator.Validator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckingAccountServiceImplements implements CheckingAccountService {

    private final Validator validator;

    private final AccountRepository accountRepository;

    @Override
    public double checkingAccountTransactionFee (TransactionRequestDTO transactionRequestDTO, double amount) {
        CheckingAccount checkingAccount = (CheckingAccount) accountRepository.findById(transactionRequestDTO.getId())
                        .orElseThrow(()-> new EntityNotFoundException("Account not found"));

        amount = transactionRequestDTO.getAmount();

        validator.validateBalanceType(transactionRequestDTO, checkingAccount.getBalance(), amount);

        double interest = (amount > 200) ? amount * checkingAccount.getTransactionFee() : 0;
        double totalAmount = checkingAccount.getBalance() + amount - interest;

        checkingAccount.setBalance(totalAmount);
        accountRepository.save(checkingAccount);

        return checkingAccount.getBalance();
    }
}
