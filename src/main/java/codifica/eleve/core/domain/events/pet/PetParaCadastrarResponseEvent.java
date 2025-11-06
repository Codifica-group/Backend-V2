package codifica.eleve.core.domain.events.pet;

import codifica.eleve.core.domain.shared.StatusEvent;

import java.io.Serializable;

public class PetParaCadastrarResponseEvent implements Serializable {

    private StatusEvent status;
    private Long chatId;
    private Integer clienteId;
    private Integer petId;
    private String erro;

    public static PetParaCadastrarResponseEvent sucesso(Long chatId, Integer clienteId, Integer petId) {
        PetParaCadastrarResponseEvent event = new PetParaCadastrarResponseEvent();
        event.setStatus(StatusEvent.SUCESSO);
        event.setChatId(chatId);
        event.setClienteId(clienteId);
        event.setPetId(petId);
        return event;
    }

    public static PetParaCadastrarResponseEvent falha(Long chatId, String erro) {
        PetParaCadastrarResponseEvent event = new PetParaCadastrarResponseEvent();
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

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
