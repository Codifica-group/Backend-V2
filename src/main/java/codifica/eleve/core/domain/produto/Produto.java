package codifica.eleve.core.domain.produto;

import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;

public class Produto {
    private Id id;
    private String nome;
    private CategoriaProduto categoriaProduto;

    public Produto(String nome, CategoriaProduto categoriaProduto) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }
        this.nome = nome;
        this.categoriaProduto = categoriaProduto;
    }

    public Produto() {}

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

    public CategoriaProduto getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }
}
