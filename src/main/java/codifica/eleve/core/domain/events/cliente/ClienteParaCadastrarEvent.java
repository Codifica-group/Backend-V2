package codifica.eleve.core.domain.events.cliente;

import java.io.Serializable;

public class ClienteParaCadastrarEvent implements Serializable {

    private Long chatId;
    private String nome;
    private String telefone;
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String numeroEndereco;
    private String complemento;

    public Long getChatId() {
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

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public String getComplemento() {
        return complemento;
    }
}
