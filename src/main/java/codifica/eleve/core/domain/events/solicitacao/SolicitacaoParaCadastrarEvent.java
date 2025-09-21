package codifica.eleve.core.domain.events.solicitacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SolicitacaoParaCadastrarEvent implements Serializable {

    private Integer chatId;
    private Integer petId;
    private List<SolicitacaoServicoEvent> servicos;
    private BigDecimal valorDeslocamento;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private LocalDateTime dataHoraSolicitacao;
    private String status;

    public Integer getChatId() {
        return chatId;
    }

    public Integer getPetId() {
        return petId;
    }

    public List<SolicitacaoServicoEvent> getServicos() {
        return servicos;
    }

    public BigDecimal getValorDeslocamento() {
        return valorDeslocamento;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public String getStatus() {
        return status;
    }
}
