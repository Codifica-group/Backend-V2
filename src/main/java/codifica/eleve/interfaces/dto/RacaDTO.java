package codifica.eleve.interfaces.dto;

import codifica.eleve.interfaces.validation.SafeString;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RacaDTO {

    private Integer id;

    @SafeString
    private String nome;

    private Integer porteId;
    private String porteNome;

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

    public Integer getPorteId() {
        return porteId;
    }

    public void setPorteId(Integer porteId) {
        this.porteId = porteId;
    }

    public String getPorteNome() {
        return porteNome;
    }

    public void setPorteNome(String porteNome) {
        this.porteNome = porteNome;
    }
}
