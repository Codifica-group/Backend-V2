package codifica.eleve.core.domain.agenda.calculadora;

import codifica.eleve.core.domain.agenda.deslocamento.Deslocamento;
import codifica.eleve.core.domain.servico.Servico;
import java.math.BigDecimal;
import java.util.List;

public class SugestaoServico {

    private final BigDecimal valor;
    private final List<Servico> servicos;
    private final Deslocamento deslocamento;

    public SugestaoServico(BigDecimal valor, List<Servico> servicos, Deslocamento deslocamento) {
        this.valor = valor;
        this.servicos = servicos;
        this.deslocamento = deslocamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public Deslocamento getDeslocamento() {
        return deslocamento;
    }
}
