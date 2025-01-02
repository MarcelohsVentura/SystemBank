import java.util.Date;

public class Transacao {
    private double valorTransacao;
    private Date data;
    private Conta contaOrigem;
    private Conta contaDestino;
    private double saldoAnterior;
    private double saldoPosterior;
    private Banco banco;
    private TipoTransacao tipoTransacao;
    private TipoConta tipoConta;

    public Transacao(double valorTransacao, Conta contaOrigem, Conta contaDestino, Banco banco) {
        this.valorTransacao = valorTransacao;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.data = new Date();
        this.banco = banco;
    }

    // Precisamos receber um valor e atribuir a uma contaDestino. O método precisa que exista um banco, e que o cliente seja autenticado. Passamos o TipoTransicao para ser definido na operacao (classe main)
    public void depositar(double valor, Conta contaDestino, Banco banco, TipoTransacao tipoTransacao) {
        // Verificando e validando valor do depósito para não ser igual a zero ou negativo
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }
        // Salvando o saldo antes da lógica
        saldoAnterior = contaDestino.getSaldo();
        // Pegando o valor da contaDestino e atribuindo ao saldoAtual
        double saldoAtual = contaDestino.getSaldo();
        // Somando o saldoAtual com o valor do depósito e atribuindo a contaDestino
        contaDestino.setSaldo(saldoAtual + valor);
        // Salvando o saldo após a lógica
        saldoPosterior = contaDestino.getSaldo();
        // Atribuindo o tipo de transação;
        this.tipoTransacao = TipoTransacao.DEPOSITO;
    }

    // Precisamos receber um valor e atribui-lo ao contaOrigem, e que o cliente seja autenticado. Passamos o TipoTransicao para ser definido na operacao (classe main)
    public void sacar(double valor, Conta contaOrigem, TipoTransacao tipoTransacao) {
        // Verificando e validando se o saldo é negativo ou zero, se o valor de saque é menor que zero ou negativo e se o valor é maior que o saldo da conta de origem
        if (contaOrigem.getSaldo() <= 0 || valor <= 0 || valor > contaOrigem.getSaldo()) {
            throw new IllegalArgumentException("Saldo ou valor de retirada inválidos");
        }

        // Salvando o saldo antes da lógica
        saldoAnterior = contaOrigem.getSaldo();
        // Atribuindo o saldo a variável saldoAtual
        double saldoAtual = contaOrigem.getSaldo();
        // Subtraindo o valor do saldoAtual e atribuindo a contaOrigem
        contaOrigem.setSaldo(saldoAtual - valor);
        // Salvando o saldo após a lógica
        saldoPosterior = contaOrigem.getSaldo();
        // Atribuindo o tipo de transação;
        this.tipoTransacao = TipoTransacao.SAQUE;
    }

    // Precisamos receber um valor para atribuir a contaOrigem, subtrair dela e adicionar a contaDestino, além da conta ser verificada. Passamos o TipoTransicao para ser definido na operacao (classe main)
    public void transferir(double valor, Conta contaOrigem, Conta contaDestino,TipoTransacao tipoTransacao) {
        // Verificando e validando se o saldo é negativo ou zero, se o valor de transferencia é menor que zero ou negativo e se o valor é maior que o saldo da conta de origem
        if (contaOrigem.getSaldo() <= 0 || valor <= 0 || valor > contaOrigem.getSaldo()) {
            throw new IllegalArgumentException("Saldo ou valor de retirada inválidos ");
        }

        // Salvando o saldo antes da lógica
        saldoAnterior = contaOrigem.getSaldo();
        // Atribuindo o saldo a variável saldoAtual
        double saldoAtual = contaOrigem.getSaldo();
        // Salvando o saldo após a lógica
        double saldoFinal = contaDestino.getSaldo();
        // Subtraindo o valor do saldoAtual e atribuindo a contaOrigem
        contaOrigem.setSaldo(saldoAtual - valor);
        // Somando o valor do saldoAtual e atribuindo a contaDestino
        contaDestino.setSaldo(saldoFinal + valor);
        // Salvando o saldo após a lógica
        saldoPosterior = contaOrigem.getSaldo();
        // Atribuindo o tipo de transação;
        this.tipoTransacao = TipoTransacao.TRANSFERENCIA;
    }

    // Métodos getters
    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public Date getData() {
        return data;
    }

    public double getValorTransacao() {
        return valorTransacao;
    }

    public double getSaldoAnterior() {
        return saldoAnterior;
    }

    public double getSaldoPosterior() {
        return saldoPosterior;
    }

    public Banco getBanco() {
        return banco;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }
}
