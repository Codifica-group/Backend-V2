package codifica.eleve.core.domain.produto.categoria;

import codifica.eleve.core.domain.shared.Id;

public class CategoriaProduto {
    private Id id;
    private String nome;

    public CategoriaProduto(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria não pode ser vazio.");
        }
        this.nome = nome;
    }

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
