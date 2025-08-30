package codifica.eleve.core.domain.cliente;

import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Telefone;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;

public class Cliente {
    private Id id;
    private String nome;
    private Telefone telefone;
    private Endereco endereco;

    public Cliente(String nome, Telefone telefone, Endereco endereco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Cliente() {}

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
