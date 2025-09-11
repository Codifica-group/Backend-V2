package codifica.eleve.interfaces.dto;

import codifica.eleve.interfaces.validation.SafeString;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaProdutoDTO {

    private Integer id;

    @SafeString
    private String nome;

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
