package com.marceloventura.BankSystem.service.transactionService;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;
import com.marceloventura.BankSystem.dto.response.TransactionResponseDTO;
import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.Transaction;
import com.marceloventura.BankSystem.enumeration.TransactionType;
import com.marceloventura.BankSystem.repository.AccountRepository;
import com.marceloventura.BankSystem.repository.TransactionRepository;
import com.marceloventura.BankSystem.util.validator.Validator;
import com.marceloventura.BankSystem.util.mapper.TransactionMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Primary
@RequiredArgsConstructor
public class TransactionServiceImplements implements TransactionService {

    private final TransactionMapper transactionMapper;

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    @Override
    public TransactionResponseDTO findById (Long id) {
        return transactionMapper.toTransactionResponseDTO(returnTransaction(id));
    }

    @Override
    public void processTransaction (TransactionRequestDTO transactionRequestDTO) {
        TransactionProcessorFactory transactionProcessorFactory = new TransactionProcessorFactory(accountRepository);
        Transaction transaction = transactionRepository.findById(transactionRequestDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found "));

        Account account = transaction.getDestinationAccount();

        TransactionProcessor processor = transactionProcessorFactory.getProcessor(transactionRequestDTO.getTransactionType());

        processor.process(account, transactionRequestDTO);

        accountRepository.save(account);

        registerTransaction(account, transactionRequestDTO.getAmount(), transactionRequestDTO.getTransactionType());

    }

    private void registerTransaction (Account account, double amount, TransactionType transactionType) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionType)
                .amount(amount)
                .date(new Date())
                .destinationAccount(account)
                .build();

        transactionRepository.save(transaction);
    }

    private Transaction returnTransaction (Long id) {
        return transactionRepository.findById(id).orElseThrow(()-> new RuntimeException("Transaction wasn't found on database"));
    }

}
