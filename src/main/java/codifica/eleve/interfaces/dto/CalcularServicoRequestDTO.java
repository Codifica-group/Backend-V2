package codifica.eleve.interfaces.dto;

import java.util.List;

public class CalcularServicoRequestDTO {

    private Integer petId;
    private List<ServicoDTO> servicos;

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public List<ServicoDTO> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoDTO> servicos) {
        this.servicos = servicos;
    }
}
