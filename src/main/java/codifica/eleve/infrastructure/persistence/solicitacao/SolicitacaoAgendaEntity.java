package codifica.eleve.infrastructure.persistence.solicitacao;

import codifica.eleve.infrastructure.persistence.pet.PetEntity;
import codifica.eleve.infrastructure.persistence.solicitacao.agenda_servico.SolicitacaoAgendaServicoEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "solicitacao_agenda")
public class SolicitacaoAgendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long chatId;

    @ManyToOne
    private PetEntity pet;

    @OneToMany(
            mappedBy = "solicitacaoAgenda",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SolicitacaoAgendaServicoEntity> servicos;

    private BigDecimal valorDeslocamento;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String status;
    private LocalDateTime dataHoraSolicitacao;

    public SolicitacaoAgendaEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public PetEntity getPet() {
        return pet;
    }

    public void setPet(PetEntity pet) {
        this.pet = pet;
    }

    public List<SolicitacaoAgendaServicoEntity> getServicos() {
        return servicos;
    }

    public void setServicos(List<SolicitacaoAgendaServicoEntity> servicos) {
        this.servicos = servicos;
    }

    public BigDecimal getValorDeslocamento() {
        return valorDeslocamento;
    }

    public void setValorDeslocamento(BigDecimal valorDeslocamento) {
        this.valorDeslocamento = valorDeslocamento;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }
}
