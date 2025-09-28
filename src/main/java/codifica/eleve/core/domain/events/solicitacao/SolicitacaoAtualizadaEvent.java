package codifica.eleve.core.domain.events.solicitacao;

import codifica.eleve.interfaces.dto.SolicitacaoAgendaDTO;

import java.io.Serializable;

public class SolicitacaoAtualizadaEvent implements Serializable {

    private String status;
    private SolicitacaoAgendaDTO solicitacao;
    private boolean dataInicioAlterada;

    public SolicitacaoAtualizadaEvent(String status, SolicitacaoAgendaDTO solicitacao, boolean dataInicioAlterada) {
        this.status = status;
        this.solicitacao = solicitacao;
        this.dataInicioAlterada = dataInicioAlterada;
    }

    public String getStatus() {
        return status;
    }

    public SolicitacaoAgendaDTO getSolicitacao() {
        return solicitacao;
    }

    public boolean isDataInicioAlterada() {
        return dataInicioAlterada;
    }
}
