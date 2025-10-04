package codifica.eleve.core.domain.events.solicitacao;

import codifica.eleve.core.domain.shared.StatusEvent;

import java.io.Serializable;

public class SolicitacaoAceitaResponseEvent implements Serializable {

    private StatusEvent status;
    private Integer chatId;
    private String erro;

    public static SolicitacaoAceitaResponseEvent sucesso(Integer chatId) {
        SolicitacaoAceitaResponseEvent event = new SolicitacaoAceitaResponseEvent();
        event.setStatus(StatusEvent.SUCESSO);
        event.setChatId(chatId);
        return event;
    }

    public static SolicitacaoAceitaResponseEvent falha(Integer chatId, String erro) {
        SolicitacaoAceitaResponseEvent event = new SolicitacaoAceitaResponseEvent();
        event.setStatus(StatusEvent.FALHA);
        event.setChatId(chatId);
        event.setErro(erro);
        return event;
    }

    public StatusEvent getStatus() {
        return status;
    }

    public void setStatus(StatusEvent status) {
        this.status = status;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
