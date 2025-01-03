import java.util.Scanner;

public class Sistema {
    private Banco banco;
    private Scanner scanner;

    public Sistema(Banco banco) {
        this.banco = banco;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("Bem-vindo ao Sistema Bancário!");

        // Realiza a autenticação do cliente
        Cliente clienteAutenticado = autenticarCliente();

        boolean executando = true;
        while (executando) {
            System.out.println("""
                Escolha uma opção:
                1. Depósito
                2. Saque
                3. Transferência
                4. Extrato
                5. Sair
                """);
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do scanner

            switch (opcao) {
                case 1 -> realizarDeposito(clienteAutenticado);
                case 2 -> realizarSaque(clienteAutenticado);
                case 3 -> realizarTransferencia(clienteAutenticado);
                case 4 -> imprimirExtrato(clienteAutenticado);
                case 5 -> {
                    System.out.println("Saindo do sistema. Até logo!");
                    executando = false;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private Cliente autenticarCliente() {
        System.out.println("Digite seu CPF:");
        String cpf = scanner.nextLine();
        System.out.println("Digite sua senha:");
        String senha = scanner.nextLine();

        Cliente cliente = banco.getAutenticacao().autenticar(cpf, senha);
        if (cliente != null) {
            System.out.println("Autenticação bem-sucedida.");
        } else {
            System.out.println("Falha na autenticação.");
        }
        return cliente; // Retorna o cliente autenticado ou null se a autenticação falhou
    }

    private void realizarDeposito(Cliente cliente) {
        // Verifica se o cliente foi autenticado
        if (cliente == null) {
            System.out.println("A autenticação falhou. Não é possível realizar a operação.");
            return;
        }

        System.out.println("Digite o número da conta destino:");
        String numeroContaDestino = scanner.nextLine();
        System.out.println("Digite o valor do depósito:");
        double valor = scanner.nextDouble();

        // Busca a conta destino
        Conta contaDestino = banco.getClientes().stream()
                .map(Cliente::getConta)
                .filter(c -> c.getNumeroConta().equals(numeroContaDestino))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Conta destino não encontrada."));

        Conta contaOrigem = cliente.getConta();

        // Realiza a transferência
        Transacao transacao = new Transacao(valor, contaOrigem, contaDestino, banco);
        transacao.depositar(valor, contaDestino, banco, TipoTransacao.DEPOSITO);

        // Salva e exibe o extrato
        Extrato extrato = new Extrato(transacao);
        extrato.salvarExtratoEmArquivo("extrato.txt");
        System.out.println(extrato);
    }

    private void realizarSaque(Cliente cliente) {
        // Verifica se o cliente foi autenticado
        if (cliente == null) {
            System.out.println("A autenticação falhou. Não é possível realizar a operação.");
            return;
        }

        System.out.println("Digite o valor do saque:");
        double valor = scanner.nextDouble();
        Conta conta = cliente.getConta();

        // Realiza o saque
        Transacao transacao = new Transacao(valor, conta, null, banco);
        transacao.sacar(valor, conta, TipoTransacao.SAQUE);

        // Salva e exibe o extrato
        Extrato extrato = new Extrato(transacao);
        extrato.salvarExtratoEmArquivo("extrato.txt");
        System.out.println(extrato);
    }

    private void realizarTransferencia(Cliente cliente) {
        // Verifica se o cliente foi autenticado
        if (cliente == null) {
            System.out.println("A autenticação falhou. Não é possível realizar a operação.");
            return;
        }

        System.out.println("Digite o número da conta destino:");
        String numeroContaDestino = scanner.nextLine();
        System.out.println("Digite o valor da transferência:");
        double valor = scanner.nextDouble();

        // Busca a conta destino
        Conta contaDestino = banco.getClientes().stream()
                .map(Cliente::getConta)
                .filter(c -> c.getNumeroConta().equals(numeroContaDestino))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Conta destino não encontrada."));

        Conta contaOrigem = cliente.getConta();

        // Realiza a transferência
        Transacao transacao = new Transacao(valor, contaOrigem, contaDestino, banco);
        transacao.transferir(valor, contaOrigem, contaDestino, TipoTransacao.TRANSFERENCIA);

        // Salva e exibe o extrato
        Extrato extrato = new Extrato(transacao);
        extrato.salvarExtratoEmArquivo("extrato.txt");
        System.out.println(extrato);
    }

    private void imprimirExtrato(Cliente cliente) {
        // Verifica se o cliente foi autenticado
        if (cliente == null) {
            System.out.println("A autenticação falhou. Não é possível acessar o extrato.");
            return;
        }

        System.out.println("Extrato da conta:");
        try (Scanner fileScanner = new Scanner(new java.io.File("extrato.txt"))) {
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Erro ao acessar o extrato: " + e.getMessage());
        }
    }
}
