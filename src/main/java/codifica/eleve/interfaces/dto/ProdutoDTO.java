package codifica.eleve.interfaces.dto;

import codifica.eleve.interfaces.validation.SafeString;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoDTO {
    private Integer id;

    @SafeString
    private String nome;

    private Integer categoriaId;

    private CategoriaProdutoDTO categoria;

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

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public CategoriaProdutoDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProdutoDTO categoria) {
        this.categoria = categoria;
    }
}
