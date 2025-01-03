package com.marceloventura.BankSystem.controller;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;
import com.marceloventura.BankSystem.dto.response.TransactionResponseDTO;
import com.marceloventura.BankSystem.service.transactionService.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id) {
        try {
            TransactionResponseDTO transactionResponseDTO = transactionService.findById(id);
            return ResponseEntity.ok(transactionResponseDTO);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

@PostMapping ("/deposit")
    public ResponseEntity <String> deposit (@RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            transactionService.processTransaction(transactionRequestDTO);
            return ResponseEntity.ok("Deposit sucessful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

@PostMapping ("/withdraw")
    public ResponseEntity <String> withdraw (@RequestBody TransactionRequestDTO transactionRequestDTO) {
    try {
        transactionService.processTransaction(transactionRequestDTO);
        return ResponseEntity.ok("Withdrawal sucessful");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

@PostMapping ("/transfer")
    public ResponseEntity <String> transfer (@RequestBody TransactionRequestDTO transactionRequestDTO) {
    try {
        transactionService.processTransaction(transactionRequestDTO);
        return ResponseEntity.ok("Transfer sucessful");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

}
