package codifica.eleve.infrastructure.persistence.agenda.agendaServico;

import codifica.eleve.infrastructure.persistence.agenda.AgendaEntity;
import codifica.eleve.infrastructure.persistence.servico.ServicoEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "agenda_servico")
public class AgendaServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_id")
    private AgendaEntity agenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id")
    private ServicoEntity servico;

    @Column(nullable = false)
    private BigDecimal valor;

    public AgendaServicoEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AgendaEntity getAgenda() {
        return agenda;
    }

    public void setAgenda(AgendaEntity agenda) {
        this.agenda = agenda;
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
