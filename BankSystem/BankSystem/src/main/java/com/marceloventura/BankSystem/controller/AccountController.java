package com.marceloventura.BankSystem.controller;

import com.marceloventura.BankSystem.dto.request.AccountRequestDTO;
import com.marceloventura.BankSystem.dto.response.AccountResponseDTO;
import com.marceloventura.BankSystem.service.accountService.AccountService;
import com.marceloventura.BankSystem.util.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
        try {
            AccountResponseDTO accountResponseDTO = accountService.findById(id);
            return ResponseEntity.ok(accountResponseDTO);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        try {
            AccountResponseDTO accountResponseDTO = accountService.createAccount(accountRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(accountResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> update(
            @RequestBody AccountRequestDTO accountRequestDTO,
            @PathVariable Long id,
            @RequestParam String password) {

        try {
            AccountResponseDTO updatedAccount = accountService.update(id, accountRequestDTO, password);
            return ResponseEntity.ok(updatedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(
            @PathVariable Long id,
            @RequestParam String password) {
        try {
            String message = accountService.deleteAccount(id, password);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the account.");
        }
    }
}
