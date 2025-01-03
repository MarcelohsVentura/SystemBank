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

        Account sourceAccount = accountRepository.findById(transactionRequestDTO.getSourceAccountId())
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        Account destinationAccount = null;
        if (transactionRequestDTO.getTransactionType() == TransactionType.TRANSFER) {
            destinationAccount = accountRepository.findById(transactionRequestDTO.getDestinationAccountId())
                    .orElseThrow(() -> new RuntimeException("Destination account not found"));
        }

        TransactionProcessorFactory transactionProcessorFactory = new TransactionProcessorFactory(accountRepository);


        TransactionProcessor processor = transactionProcessorFactory.getProcessor(transactionRequestDTO.getTransactionType());

        processor.process(sourceAccount, destinationAccount, transactionRequestDTO);

        accountRepository.save(sourceAccount);
        if (destinationAccount != null) {
            accountRepository.save(destinationAccount);
        }

        registerTransaction(sourceAccount, destinationAccount, transactionRequestDTO.getAmount(), transactionRequestDTO.getTransactionType());

    }

    private void registerTransaction (Account sourceAccount, Account destinationAccount, double amount, TransactionType transactionType) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionType)
                .amount(amount)
                .date(new Date())
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount)
                .build();

        transactionRepository.save(transaction);
    }

    private Transaction returnTransaction (Long id) {
        return transactionRepository.findById(id).orElseThrow(()-> new RuntimeException("Transaction wasn't found on database"));
    }

}
