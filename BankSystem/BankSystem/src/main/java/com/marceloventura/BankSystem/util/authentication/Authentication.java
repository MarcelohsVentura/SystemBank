package com.marceloventura.BankSystem.util.authentication;

import com.marceloventura.BankSystem.entity.Bank;
import com.marceloventura.BankSystem.entity.Client;

public class Authentication {

    private static Authentication instantia;

    private Bank bank;

    private Authentication (Bank bank) {
        this.bank = bank;
    }

    public static Authentication getInstantia() {
        if (instantia == null) {
            throw new IllegalStateException("Uninitialized instance.");
        }
        return instantia;
    }

    public static void boot (Bank bank) {
        if (instantia == null) {
            instantia = new Authentication(bank); // Cria a instancia
        } else {
            throw new IllegalStateException("The instance has already been initialized.");
        }
    }

    public Client authenticate(String idNumber, String password) {
        for (Client client : bank.getClients()) {
            if (client.getIdNumber().equals(idNumber) && client.getPassword().equals(password)) {
                return client; // Cliente autenticado
            }
        }
        throw new IllegalArgumentException("Wrong ID number or password.");
    }

}
