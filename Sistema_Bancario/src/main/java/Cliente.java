public class Cliente {
    private String nome;
    private String cpf;
    private String senha;
    private Conta conta;

    public Cliente(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }
    // Sobrecarga de construtor
    public Cliente(String nome, String cpf, String senha, Conta conta) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.conta = conta;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
