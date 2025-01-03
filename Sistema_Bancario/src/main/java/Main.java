public class Main {
    public static void main(String[] args) {
        // Criando o banco
        Banco banco = new Banco("Banco Exemplo", "1234");

        // Criando clientes e contas
        Cliente cliente1 = new Cliente("João Silva", "123", "123");
        Conta conta1 = new Conta("0001", 1000.00, cliente1, TipoConta.CORRENTE);
        cliente1.setConta(conta1);

        Cliente cliente2 = new Cliente("Maria Souza", "98765432100", "senha456");
        Conta conta2 = new Conta("0002", 2000.00, cliente2, TipoConta.POUPANCA);
        cliente2.setConta(conta2);

        // Adicionando clientes ao banco
        banco.adicionarCliente(cliente1);
        banco.adicionarCliente(cliente2);

        // Inicializando o Singleton de Autenticacao
        Autenticacao.inicializar(banco);

        // Criando o sistema
        Sistema sistema = new Sistema(banco);

        // Iniciando o sistema
        sistema.iniciar();
    }
}
