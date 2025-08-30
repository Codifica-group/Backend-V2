package codifica.eleve.core.domain.usuario;

import codifica.eleve.core.domain.shared.Email;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Senha;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;

public class Usuario {
    private Id id;
    private String nome;
    private Email email;
    private Senha senha;
    private String senhaCodificada;

    public Usuario(String nome, Email email, Senha senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {}

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

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Senha getSenha() {
        return senha;
    }

    public void setSenha(Senha senha) {
        this.senha = senha;
    }

    public String getSenhaCodificada() {
        return senhaCodificada;
    }

    public void setSenhaCodificada(String senhaCodificada) {
        this.senhaCodificada = senhaCodificada;
    }
}