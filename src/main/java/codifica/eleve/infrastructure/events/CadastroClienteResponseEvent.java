package codifica.eleve.infrastructure.events;

import codifica.eleve.core.domain.shared.StatusEvent;

import java.io.Serializable;

public class CadastroClienteResponseEvent implements Serializable {

    private StatusEvent status;
    private Integer chatId;
    private Integer clienteId;
    private String erro;

    public static CadastroClienteResponseEvent sucesso(Integer chatId, Integer clienteId) {
        CadastroClienteResponseEvent event = new CadastroClienteResponseEvent();
        event.setStatus(StatusEvent.SUCESSO);
        event.setChatId(chatId);
        event.setClienteId(clienteId);
        return event;
    }

    public static CadastroClienteResponseEvent falha(Integer chatId, String erro) {
        CadastroClienteResponseEvent event = new CadastroClienteResponseEvent();
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

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
