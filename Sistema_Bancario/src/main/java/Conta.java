public class Conta {
    private String numeroConta;
    private double saldo;
    private Cliente cliente;
    private TipoConta tipoConta;

    public Conta(String numeroConta, double saldo, Cliente cliente, TipoConta tipoConta) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.cliente = cliente;
        this.tipoConta = tipoConta;
    }
    // Sobrecarga de construtor
    public Conta(String numeroConta, double saldo, Cliente cliente, TipoConta tipoConta, Autenticacao autenticacao) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.cliente = cliente;
        this.tipoConta = tipoConta;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

}
