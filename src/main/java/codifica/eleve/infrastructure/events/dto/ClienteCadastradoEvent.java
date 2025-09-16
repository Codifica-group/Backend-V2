package codifica.eleve.infrastructure.events.dto;

import java.io.Serializable;

public class ClienteCadastradoEvent implements Serializable {
    private Integer chatId;
    private Integer clienteId;
    private String status = "CLIENTE_CADASTRADO";

    public ClienteCadastradoEvent(Integer chatId, Integer clienteId) {
        this.chatId = chatId;
        this.clienteId = clienteId;
    }

    public ClienteCadastradoEvent() {}

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
