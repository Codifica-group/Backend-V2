package codifica.eleve.core.domain.events.pet;

import java.io.Serializable;

public class PetParaCadastrarEvent implements Serializable {

    private Integer chatId;
    private Integer clienteId;
    private String nome;
    private String raca;

    public Integer getChatId() {
        return chatId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }
}
