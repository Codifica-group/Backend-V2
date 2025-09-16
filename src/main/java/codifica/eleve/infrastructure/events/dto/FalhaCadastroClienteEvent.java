package codifica.eleve.infrastructure.events.dto;

import java.io.Serializable;

public class FalhaCadastroClienteEvent implements Serializable {
    private Integer chatId;
    private String erro;

    public FalhaCadastroClienteEvent(Integer chatId, String erro) {
        this.chatId = chatId;
        this.erro = erro;
    }

    public FalhaCadastroClienteEvent() {}

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
