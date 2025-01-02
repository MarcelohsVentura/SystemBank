package com.marceloventura.BankSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marceloventura.BankSystem.controller.BankController;
import com.marceloventura.BankSystem.dto.request.BankResquestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//@SpringBootTest
@WebMvcTest(BankController.class)
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateBank() throws Exception {
        // Criação de um BankRequestDTO para a requisição
        BankResquestDTO bankRequestDTO = new BankResquestDTO();
        bankRequestDTO.setBankName("Bank of Example");
        bankRequestDTO.setAgencyNumber("12345");

        // Enviar a requisição POST com o objeto BankRequestDTO no corpo
        mockMvc.perform(post("/banks")
                        .contentType(MediaType.APPLICATION_JSON)  // Definindo o tipo de conteúdo como JSON
                        .content(objectMapper.writeValueAsString(bankRequestDTO)))  // Convertendo o DTO para JSON
                .andExpect(MockMvcResultMatchers.status().isCreated())  // Verificando o status de criação
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").value("Bank of Example"))  // Verificando o nome do banco
                .andExpect(MockMvcResultMatchers.jsonPath("$.agencyNumber").value("12345"));  // Verificando o número da agência
    }
}
