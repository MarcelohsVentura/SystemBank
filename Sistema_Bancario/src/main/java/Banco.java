import java.util.ArrayList;
import java.util.List;

public class Banco {
    private String nomeBanco;
    private String numeroAgencia;
    private List<Cliente> clientes;

    public Banco(String nomeBanco, String numeroAgencia) {
        this.nomeBanco = nomeBanco;
        this.numeroAgencia = numeroAgencia;
        // Inicializando a lista de clientes, para armazenar os clientes nela.
        this.clientes = new ArrayList<>();

    }

    public Banco(String nomeBanco, String numeroAgencia, List<Cliente> clientes) {
        this.nomeBanco = nomeBanco;
        this.numeroAgencia = numeroAgencia;
        this.clientes = clientes;
    }

    // Adiciona clientes na lista cliente. Primeiro, recebe um Cliente cliente, e o passamos por meio do add (cliente) para o clientes
    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }


    // Por ser Singleton, precisa ter um método para retornar a única instância que será criada (foi criado na própria classe Autenticacao pelo método getInstancia()), permitindo que outras classes possam utilizar a instancia.
    // Mas decidi para não haver o acesso direto a esta instancia, por parte das outras classes. Assim, escolhi a classe Banco para ponto de acesso a classe Autenticacao (pois na minha cabeça, banco que autentica)
    // Assim, criei um método getAutenticacao que encapsula a lógica de acesso a instancia Singleto. Desse forma, para outra classe acessar esta instancia, terá que ter obrigatoriamente um objeto do tipo Banco, ou seja, apenas classes que possuem acesso a classe Banco poderão acessar a instancia de Autenticacao.
    public Autenticacao getAutenticacao() {
        // Por conta da classe Autenticacao seguir padrão Singleton, ela só terá uma instância. Dessa forma, o Autenticacao.getInstancia verifica se essa instância já foi criada  (na classe main pelo método inicilizar) e caso sim, retornará essa instância. Caso não tiver sido, será inicializada
        // Assim que criada a instancia, ela será armazenada em uma variável privada estática dentro da própria classe Autenticacao ( private static Autenticacao instancia;)
        return Autenticacao.getInstancia();
    }
}
