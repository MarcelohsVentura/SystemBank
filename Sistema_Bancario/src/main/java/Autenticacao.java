public class Autenticacao {
    // Vai armazenar a instancia única que é criada
    private static Autenticacao instancia;
    // Usado para pegar a lista de clientes da classe Banco
    private Banco banco;

    // Construtor privado para garantir que a classe seja Singleton, ou seja, para que ela não seja instanciada diretamente
    private Autenticacao(Banco banco) {
        this.banco = banco;
    }

    // Método estático para obter a instância única de Autenticacao (verificar se foi criado a instancia na classe main)
    public static Autenticacao getInstancia() {
        if (instancia == null) {
            throw new IllegalStateException("A instância não inicializada.");
        }
        return instancia;
    }

    // Método para inicializar a instância do Singleton. Ela foi inicializada na classe Main.
    public static void inicializar(Banco banco) {
        if (instancia == null) {
            instancia = new Autenticacao(banco); // Cria a instancia
        } else {
            throw new IllegalStateException("A instância já foi inicializada.");
        }
    }

    // Método de autenticação que valida o CPF e a senha. Usado na classe Sistema.
    public Cliente autenticar(String cpf, String senha) {
        for (Cliente cliente : banco.getClientes()) {
            if (cliente.getCpf().equals(cpf) && cliente.getSenha().equals(senha)) {
                return cliente; // Cliente autenticado
            }
        }
        throw new IllegalArgumentException("CPF ou senha inválidos.");
    }

}
// Por que singleton? Para não haver conflito de estado (já que só existe uma instancia e todos a utilizam), e toda a lógica é gerenciada em um único lugar.