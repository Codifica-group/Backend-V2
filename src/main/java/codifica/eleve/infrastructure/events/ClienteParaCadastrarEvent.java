package codifica.eleve.infrastructure.events;

import codifica.eleve.core.domain.shared.Endereco;

import java.io.Serializable;

public class ClienteParaCadastrarEvent implements Serializable {

    private Integer chatId;
    private String nome;
    private String telefone;
    private Endereco endereco;

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
