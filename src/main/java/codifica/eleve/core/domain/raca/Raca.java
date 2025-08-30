package codifica.eleve.core.domain.raca;

import codifica.eleve.core.domain.raca.porte.Porte;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;

public class Raca {
    private Id id;
    private String nome;
    private Porte porte;

    public Raca(String nome, Porte porte) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da raça não pode ser vazio.");
        }
        if (porte == null) {
            throw new IllegalArgumentException("O porte da raça não pode ser nulo.");
        }
        this.nome = nome;
        this.porte = porte;
    }

    public Raca() {}

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

    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }
}