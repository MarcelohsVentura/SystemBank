import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Extrato {
    private Transacao transacao;

    public Extrato(Transacao transacao) {
        this.transacao = transacao;
    }

    @Override
    public String toString() {
        StringBuilder extrato = new StringBuilder();
        extrato.append("Data da Transação: ").append(transacao.getData()).append("\n");
        extrato.append("Tipo de Transação: ").append(transacao.getTipoTransacao()).append("\n");
        extrato.append("Valor da Transação: R$ ").append(transacao.getValorTransacao()).append("\n");
        extrato.append("Saldo Anterior: R$ ").append(transacao.getSaldoAnterior()).append("\n");
        extrato.append("Saldo Posterior: R$ ").append(transacao.getSaldoPosterior()).append("\n");

        // Verificar se a contaDestino existe antes de tentar acessar
        if (transacao.getContaDestino() != null) {
            extrato.append("Conta Destino: ").append(transacao.getContaDestino().getNumeroConta()).append("\n");
        } else {
            extrato.append("Conta Destino: Não se aplica para este tipo de transação (Saque)\n");
        }

        extrato.append("Conta Origem: ").append(transacao.getContaOrigem().getNumeroConta()).append("\n");

        return extrato.toString();
    }

    public void salvarExtratoEmArquivo(String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, true))) {
            writer.println(this.toString());
        } catch (IOException e) {
            System.out.println("Erro ao salvar o extrato: " + e.getMessage());
        }
    }
}
