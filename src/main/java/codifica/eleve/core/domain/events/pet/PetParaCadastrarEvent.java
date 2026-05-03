package codifica.eleve.core.domain.events.pet;

import codifica.eleve.interfaces.dto.RacaDTO;

import java.io.Serializable;

public class PetParaCadastrarEvent implements Serializable {

    private Long chatId;
    private Integer clienteId;
    private String nome;
    private String sexo;
    private String foto;
    private RacaDTO raca;

    public Long getChatId() {
        return chatId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public String getNome() {
        return nome;
    }

    public String getSexo() {
        return sexo;
    }

    public String getFoto() {
        return foto;
    }

    public RacaDTO getRaca() {
        return raca;
    }
}
