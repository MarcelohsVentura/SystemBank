package com.marceloventura.BankSystem.entity;
    /*
    mappedBy = "cliente" Define o lado não proprietário do relacionamento. Isso significa que a entidade Conta possui a propriedade que define o vínculo com Cliente.
    cascade = CascadeType.ALL Configura o comportamento em cascata, ou seja, determina como as operações realizadas em Cliente serão propagadas para as entidades relacionadas (Conta). Nesse caso, CascadeType.ALL significa que:

    Persistência (PERSIST): Quando um Cliente for salvo no banco de dados, todas as suas contas associadas também serão salvas automaticamente.
    Atualização (MERGE): Atualizações no cliente serão propagadas para suas contas.
    Remoção (REMOVE): Ao remover um cliente, todas as contas associadas também serão removidas.
    Refrescamento (REFRESH): Se o cliente for recarregado do banco, as contas associadas também serão atualizadas.
    Desanexação (DETACH): Ao desanexar o cliente, as contas associadas também serão desanexadas.

    orphanRemoval = true Esse atributo significa que se uma conta for removida da lista de contas do cliente, ela será automaticamente deletada do banco de dados.
     */
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "tb-client")
@NoArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "clientId", nullable = false, unique = true)
    @Setter (AccessLevel.NONE)
    private Long id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "idNumber", nullable = false)
    private String idNumber;    // Verificar se o cpf é do mesmo nome

    @Column (name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "client", orphanRemoval = true)
    private List <Account> accounts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "bankId", nullable = false)
    private Bank bank;

    @Builder
    public Client(String name, String idNumber, String password, List<Account> accounts, Bank bank) {
        this.name = name;
        this.idNumber = idNumber;
        this.password = password;
        this.accounts = accounts;
        this.bank = bank;
    }
}
