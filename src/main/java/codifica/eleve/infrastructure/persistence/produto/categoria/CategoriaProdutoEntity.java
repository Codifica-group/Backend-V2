package codifica.eleve.infrastructure.persistence.produto.categoria;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria_produto")
public class CategoriaProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    public CategoriaProdutoEntity(String nome) {
        this.nome = nome;
    }

    public CategoriaProdutoEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
