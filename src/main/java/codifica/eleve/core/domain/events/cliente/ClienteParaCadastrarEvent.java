package codifica.eleve.core.domain.events.cliente;

import java.io.Serializable;

public class ClienteParaCadastrarEvent implements Serializable {

    private Integer chatId;
    private String nome;
    private String telefone;
    private String cep;
    private String numeroEndereco;
    private String complemento;

    public Integer getChatId() {
        return chatId;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public String getComplemento() {
        return complemento;
    }
}
