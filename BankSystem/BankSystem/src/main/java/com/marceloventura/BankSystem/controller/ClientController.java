package com.marceloventura.BankSystem.controller;


import com.marceloventura.BankSystem.dto.request.ClientRequestDTO;
import com.marceloventura.BankSystem.dto.response.AccountResponseDTO;
import com.marceloventura.BankSystem.dto.response.ClientResponseDTO;
import com.marceloventura.BankSystem.entity.Client;
import com.marceloventura.BankSystem.service.clientService.ClientService;
import com.marceloventura.BankSystem.util.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        try {
            ClientResponseDTO clientResponseDTO = clientService.findById(id);
            return ResponseEntity.ok(clientResponseDTO);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> findAll() {
        try {
            List <ClientResponseDTO> clients = clientService.findAll();
            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient (@RequestBody ClientRequestDTO clientRequestDTO) {
        try {
            Client client = clientService.createClient(clientRequestDTO);
            ClientResponseDTO clientResponseDTO = clientMapper.toClientResponseDTO(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(
            @RequestBody ClientRequestDTO clientRequestDTO,
            @PathVariable Long id) {
        try {
            ClientResponseDTO updatedClient = clientService.update(id, clientRequestDTO);
            return ResponseEntity.ok(updatedClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String message = clientService.delete(id);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the client.");
        }
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> getAccountsByClientId(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getAccountsByClientId(id));
    }

    @PostMapping("/{clientId}/accounts/{accountId}")
    public ResponseEntity<String> addAccountToClient(
            @PathVariable Long bankId ,@PathVariable Long clientId,
            @PathVariable Long accountId) {
        try {
            clientService.addAccountToClient(bankId, accountId, clientId);
            return ResponseEntity.ok("Account with ID " + accountId + " successfully added to Bank with ID " + bankId + " and client ID " + clientId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding account to bank");
        }
    }
}
