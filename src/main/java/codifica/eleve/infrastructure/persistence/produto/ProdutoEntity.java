package codifica.eleve.infrastructure.persistence.produto;

import codifica.eleve.infrastructure.persistence.produto.categoria.CategoriaProdutoEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_produto_id")
    private CategoriaProdutoEntity categoriaProduto;

    public ProdutoEntity(String nome, CategoriaProdutoEntity categoriaProduto) {
        this.nome = nome;
        this.categoriaProduto = categoriaProduto;
    }

    public ProdutoEntity() {}

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

    public CategoriaProdutoEntity getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProdutoEntity categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }
}
