package codifica.eleve.infrastructure.persistence.solicitacao.agenda_servico;

import codifica.eleve.infrastructure.persistence.servico.ServicoEntity;
import codifica.eleve.infrastructure.persistence.solicitacao.SolicitacaoAgendaEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "solicitacao_agenda_servico")
public class SolicitacaoAgendaServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_agenda_id")
    private SolicitacaoAgendaEntity solicitacaoAgenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id")
    private ServicoEntity servico;

    private BigDecimal valor;

    public SolicitacaoAgendaServicoEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SolicitacaoAgendaEntity getSolicitacaoAgenda() {
        return solicitacaoAgenda;
    }

    public void setSolicitacaoAgenda(SolicitacaoAgendaEntity solicitacaoAgenda) {
        this.solicitacaoAgenda = solicitacaoAgenda;
    }

    public ServicoEntity getServico() {
        return servico;
    }

    public void setServico(ServicoEntity servico) {
        this.servico = servico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
