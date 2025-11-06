package codifica.eleve.core.domain.events.solicitacao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class SolicitacaoParaCadastrarEvent implements Serializable {

    private Long chatId;
    private Integer petId;
    private List<Integer> servicos;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraSolicitacao;
    private String status;

    public Long getChatId() {
        return chatId;
    }

    public Integer getPetId() {
        return petId;
    }

    public List<Integer> getServicos() {
        return servicos;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public String getStatus() {
        return status;
    }
}
