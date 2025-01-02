package com.marceloventura.BankSystem.controller;


import com.marceloventura.BankSystem.dto.request.BankResquestDTO;
import com.marceloventura.BankSystem.dto.response.BankResponseDTO;
import com.marceloventura.BankSystem.repository.BankRepository;
import com.marceloventura.BankSystem.service.bankService.BankService;
import com.marceloventura.BankSystem.util.mapper.BankMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;
    private final BankService bankService;

    @GetMapping
    public ResponseEntity <List<BankResponseDTO>> getAllBanks () {
        try {
            List <BankResponseDTO> banks = bankService.findAll();
            return ResponseEntity.ok(banks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankResponseDTO> findById (@PathVariable Long id) {
        try {
            BankResponseDTO bankResponseDTO = bankService.findById(id);
            return ResponseEntity.ok(bankResponseDTO);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

@DeleteMapping ("/{id}")
public ResponseEntity<String> delete (@PathVariable Long id) {
    try {
        String message = bankService.delete(id);
        return ResponseEntity.ok(message);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the bank.");
    }
}

    @PostMapping
    public ResponseEntity<BankResponseDTO> createBank(@RequestBody BankResquestDTO bankRequestDTO) {
        try {
            BankResponseDTO bankResponseDTO = bankService.createBank(bankRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(bankResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankResponseDTO> updateBank(
            @PathVariable Long id,
            @RequestBody BankResquestDTO bankRequestDTO) {
        try {
            BankResponseDTO updatedBank = bankService.updateBank(id, bankRequestDTO);
            return ResponseEntity.ok(updatedBank);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{bankId}/clients/{clientId}")
    public ResponseEntity<String> addClientToBank(
            @PathVariable Long bankId,
            @PathVariable Long clientId) {
        try {
            bankService.addClientToBank(bankId, clientId);
            return ResponseEntity.ok("Client with ID " + clientId + " successfully added to Bank with ID " + bankId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding client to bank");
        }
    }

    @PostMapping("/{bankId}/accounts/{accountId}")
    public ResponseEntity<String> addAccountToBank(
            @PathVariable Long bankId,
            @PathVariable Long accountId) {
        try {
            bankService.addAccountToBank(bankId, accountId);
            return ResponseEntity.ok("Account with ID " + accountId + " successfully added to Bank with ID " + bankId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding account to bank");
        }
    }

    @DeleteMapping ("/{bankId}/accounts/{accountId}")
    public ResponseEntity<String> deleteAccount (@PathVariable Long bankId,@PathVariable Long accountId) {
        try {
            bankService.deleteAccount(bankId, accountId);
            return ResponseEntity.ok("Account with ID " + accountId + " was sucessfully deleted to Bank with ID " + bankId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting account for bank");
        }
    }

    @DeleteMapping ("/{bankId}/clients/{clientId}")
    public ResponseEntity<String> deleteClient (@PathVariable Long bankId, @PathVariable Long clientId) {
        try {
            bankService.deleteClient(bankId, clientId);
            return ResponseEntity.ok("Client with ID " + clientId + " was sucessfully deleted to bank with ID " + bankId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting client for bank");
        }
    }

}

