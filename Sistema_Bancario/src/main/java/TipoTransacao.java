public enum TipoTransacao {
    DEPOSITO ("Depósito"),
    SAQUE("Saque"),
    TRANSFERENCIA ("Transferência");

    // final pois são valores que não quero que sejam alterados em nenhum momento
    private final String tipoTransacao;

    TipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }
}

