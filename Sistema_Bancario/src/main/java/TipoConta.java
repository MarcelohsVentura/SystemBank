public enum TipoConta {
    CORRENTE ("Conta corrente", "Conta usada para transações diárias"),
    POUPANCA("Conta Poupança", "Conta usada para poupar dinheiro");

    // final pois são valores que não quero que sejam alterados em nenhum momento
    // nome recebe a primeira string: Conta corrente por exemplo
    private final String nome;
    // descrição recebe a segunda String: Conta usada para transações diárias por exemplo.
    private final String descricao;

    TipoConta(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }
}
