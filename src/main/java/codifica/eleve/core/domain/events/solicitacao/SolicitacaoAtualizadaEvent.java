package codifica.eleve.core.domain.events.solicitacao;

import codifica.eleve.interfaces.dto.SolicitacaoAgendaDTO;

import java.io.Serializable;

public class SolicitacaoAtualizadaEvent implements Serializable {

    private Boolean aceito;
    private Boolean dataInicioAlterada;
    private SolicitacaoAgendaDTO solicitacao;

    public SolicitacaoAtualizadaEvent(Boolean aceito, Boolean dataInicioAlterada, SolicitacaoAgendaDTO solicitacao) {
        this.aceito = aceito;
        this.dataInicioAlterada = dataInicioAlterada;
        this.solicitacao = solicitacao;
    }

    public Boolean getAceito() {
        return aceito;
    }

    public void setAceito(Boolean aceito) {
        this.aceito = aceito;
    }

    public Boolean getDataInicioAlterada() {
        return dataInicioAlterada;
    }

    public void setDataInicioAlterada(Boolean dataInicioAlterada) {
        this.dataInicioAlterada = dataInicioAlterada;
    }

    public SolicitacaoAgendaDTO getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(SolicitacaoAgendaDTO solicitacao) {
        this.solicitacao = solicitacao;
    }
}
