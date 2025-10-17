package codifica.eleve.core.domain.events.pet;

import codifica.eleve.interfaces.dto.RacaDTO;

import java.io.Serializable;

public class PetParaCadastrarEvent implements Serializable {

    private Integer chatId;
    private Integer clienteId;
    private String nome;
    private RacaDTO raca;

    public Integer getChatId() {
        return chatId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public String getNome() {
        return nome;
    }

    public RacaDTO getRaca() {
        return raca;
    }
}
