package codifica.eleve.core.domain.events.cliente;

import codifica.eleve.core.domain.shared.StatusEvent;

import java.io.Serializable;

public class ClienteParaCadastrarResponseEvent implements Serializable {

    private StatusEvent status;
    private Integer chatId;
    private Integer clienteId;
    private String clienteNome;
    private String erro;

    public static ClienteParaCadastrarResponseEvent sucesso(Integer chatId, Integer clienteId, String clienteNome) {
        ClienteParaCadastrarResponseEvent event = new ClienteParaCadastrarResponseEvent();
        event.setStatus(StatusEvent.SUCESSO);
        event.setChatId(chatId);
        event.setClienteId(clienteId);
        event.setClienteNome(clienteNome);
        return event;
    }

    public static ClienteParaCadastrarResponseEvent falha(Integer chatId, String erro) {
        ClienteParaCadastrarResponseEvent event = new ClienteParaCadastrarResponseEvent();
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

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
