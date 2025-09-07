package codifica.eleve.core.domain.agenda;

import codifica.eleve.core.domain.shared.Periodo;
import java.util.List;

public class Filtro {
    private Periodo periodo;
    private Integer clienteId;
    private Integer petId;
    private Integer racaId;
    private List<Integer> servicoId;

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getRacaId() {
        return racaId;
    }

    public void setRacaId(Integer racaId) {
        this.racaId = racaId;
    }

    public List<Integer> getServicoId() {
        return servicoId;
    }

    public void setServicoId(List<Integer> servicoId) {
        this.servicoId = servicoId;
    }
}
