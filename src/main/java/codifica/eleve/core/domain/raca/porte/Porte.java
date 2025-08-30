package codifica.eleve.core.domain.raca.porte;

import codifica.eleve.core.domain.shared.Id;

public class Porte {
    private Id id;
    private String nome;

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
}
