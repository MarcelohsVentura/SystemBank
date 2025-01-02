package com.marceloventura.BankSystem.util.validator;

import com.marceloventura.BankSystem.dto.request.AccountRequestDTO;
import com.marceloventura.BankSystem.dto.request.BankResquestDTO;
import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;
import com.marceloventura.BankSystem.entity.*;
import com.marceloventura.BankSystem.enumeration.TransactionType;
import com.marceloventura.BankSystem.repository.BankRepository;
import com.marceloventura.BankSystem.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class Validator {

    private BankRepository bankRepository;

    private ClientRepository clientRepository;

    public static void validateDeletable (Client client) {
        boolean hasNonZeroBalance = client.getAccounts().stream()
                .anyMatch(account -> account.getBalance() != 0);

        if (hasNonZeroBalance) {
            throw  new IllegalStateException("Positive or negative balance, impossible to delete");
        }
    }

    public void validatePassword (String providedPassword, String actualPassword) {
        if (providedPassword == null || providedPassword.isEmpty()) {
            throw new IllegalArgumentException("Password is empty");
        }
        if (!providedPassword.equals(actualPassword)) {
            throw new IllegalArgumentException("Incorrect password. Not authorized");
        }
    }

    public void validadePasswordEmptyOrNull (String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password null or empty");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Password needs to have at least 6 caracters");
        }
    }

    // For account
    public void validateAccountClientPassword (String password, Client client) {
        if (!client.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong password.");
        }
    }

    public void validateAccountBalance (Account account) {
        if (account.getBalance() < 0) {
            throw new IllegalStateException("The balance can't be negative");
        }
    }

    public void validateAccountBalanceToDelete (Account account) {
        if (account.getBalance() < 0 || account.getBalance() > 0) {
            throw new IllegalStateException("The balance can't be negative or superior than zero for delete account");
        }
    }

    public void validateBalance(double balance, double amount) {
        if (balance < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
    }

        public void validateBalanceType (TransactionRequestDTO transactionRequestDTO, double balance, double amount) {
        if (transactionRequestDTO.getTransactionType() != TransactionType.DEPOSIT) {
            validateBalance(balance, amount);
        }
    }

    public void validateDeposit (double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount for deposit");
        }
        }

    public void validateAccountForTransactions(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Invalid account.");
        }
        if (account.getBalance() < 0) {
            throw new IllegalStateException("Account with negative balance can't recive deposit.");
        }
    }

    // For bank
    public Bank validateBankExist (Long bankId) {
        return bankRepository.findById(bankId)
                .orElseThrow(()-> new EntityNotFoundException("Bank not found"));
    }

    // For bank
    public void validateDeletableBank (Bank bank) {
        if (!bank.getClients().isEmpty() && !bank.getAccounts().isEmpty()) {
            throw new IllegalStateException("Impossible to delete. Bank have clients.");
        }
    }

    // For bank
    public void deleteClient (Long bankId, Long clientId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new EntityNotFoundException("Bank not found with ID: " + bankId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));

        if (!bank.getClients().contains(client)) {
            throw new IllegalStateException("Client is not associate with this bank.");
        }

        if (!client.getAccounts().isEmpty()) {
            throw new IllegalStateException("O cliente possui contas associadas e não pode ser removido.");
        }
    }

    // For bank
    public void isEmptyParametersBank(BankResquestDTO bankResquestDTO) {
        // Validando se o nome do banco ou outros parâmetros necessários estão presentes
        if (bankResquestDTO.getBankName() == null || bankResquestDTO.getBankName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank name is required");
        }
        if (bankResquestDTO.getAgencyNumber() == null || bankResquestDTO.getAgencyNumber().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agency number is required");
        }
    }

    // For account
    public void isEmptyParametersAccount (AccountRequestDTO accountRequestDTO) {
        if (accountRequestDTO.getAccountType() == null || accountRequestDTO.getAccountType().getAccountType().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account type required");
        }
        if (accountRequestDTO.getAccountNumber() == null || accountRequestDTO.getAccountNumber().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account number is required");
        }
    }

    }

