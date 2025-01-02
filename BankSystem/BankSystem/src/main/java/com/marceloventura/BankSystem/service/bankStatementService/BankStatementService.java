package com.marceloventura.BankSystem.service.bankStatementService;

import com.marceloventura.BankSystem.dto.response.BankStatementRespondeDTO;

public interface BankStatementService {

    BankStatementRespondeDTO findById (Long id);

    public String toString(Long id);

    void saveStatementOnArchive (String bankStatementArchive);
}
