package com.marceloventura.BankSystem.service.bankStatementService;

import com.marceloventura.BankSystem.dto.response.BankStatementRespondeDTO;
import com.marceloventura.BankSystem.entity.BankStatement;
import com.marceloventura.BankSystem.entity.Transaction;
import com.marceloventura.BankSystem.repository.BankStatementRepository;
import com.marceloventura.BankSystem.repository.TransactionRepository;
import com.marceloventura.BankSystem.util.mapper.BankStatementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
@Primary
@RequiredArgsConstructor
public class BankStatementServiceImplements implements BankStatementService {

    private final BankStatementMapper bankStatementMapper;

    private final TransactionRepository transactionRepository;

    private final BankStatementRepository bankStatementRepository;
// DTOS e entitys não podem estar aqui para receberem o RequiredArgsConstructor

    @Override
    public BankStatementRespondeDTO findById (Long id) {
        return bankStatementMapper.toBankStatementRespondeDTO(returnBankStatement(id));
    }

    @Override
    public String toString(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        StringBuilder bankStatement = new StringBuilder();
        bankStatement.append("Transaction date: ").append(transaction.getDate()).append("\n");
        bankStatement.append("Transaction type: ").append(transaction.getTransactionType()).append("\n");
        bankStatement.append("Transaction value: ").append(transaction.getTransactionValue()).append("\n");
        bankStatement.append("Previous balance: $ ").append(transaction.getPreviousBalance()).append("\n");
        bankStatement.append("Current balance: $ ").append(transaction.getLaterBalance()).append("\n");
        bankStatement.append("Account type: ").append(transaction.getAccountType()).append("\n");

        if (transaction.getDestinationAccount() != null) {
            bankStatement.append("Destination account: ").append(transaction.getDestinationAccount()).append("\n");
        } else {
            bankStatement.append("Destination account don't apply for this type of operation.\n");
        }
        bankStatement.append("Source account: ").append(transaction.getSourceAccount()).append("\n");

        return bankStatement.toString();
    }

    public void saveStatementOnArchive (String bankStatementArchive) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(bankStatementArchive, true))) {
            writer.println(this.toString());
        } catch (IOException e) {
            System.out.println("Error to save statement: " + e.getMessage());
        }
    }

    private BankStatement returnBankStatement (Long id) {
        return bankStatementRepository.findById(id).orElseThrow(()-> new RuntimeException("Person wasn't found on database"));
    }
}
