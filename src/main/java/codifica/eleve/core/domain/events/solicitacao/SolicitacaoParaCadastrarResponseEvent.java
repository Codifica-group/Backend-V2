package codifica.eleve.core.domain.events.solicitacao;

import codifica.eleve.core.domain.shared.StatusEvent;

import java.io.Serializable;

public class SolicitacaoParaCadastrarResponseEvent implements Serializable {

    private StatusEvent status;
    private Long chatId;
    private Integer solicitacaoId;
    private String erro;

    public static SolicitacaoParaCadastrarResponseEvent sucesso(Long chatId, Integer solicitacaoId) {
        SolicitacaoParaCadastrarResponseEvent event = new SolicitacaoParaCadastrarResponseEvent();
        event.setStatus(StatusEvent.SUCESSO);
        event.setChatId(chatId);
        event.setSolicitacaoId(solicitacaoId);
        return event;
    }

    public static SolicitacaoParaCadastrarResponseEvent falha(Long chatId, String erro) {
        SolicitacaoParaCadastrarResponseEvent event = new SolicitacaoParaCadastrarResponseEvent();
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

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Integer getSolicitacaoId() {
        return solicitacaoId;
    }

    public void setSolicitacaoId(Integer solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
