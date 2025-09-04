package codifica.eleve.core.domain.agenda;

import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import java.util.List;

public class Agenda {

    private Id id;
    private Pet pet;
    private List<Servico> servicos;
    private ValorMonetario valorDeslocamento;
    private Periodo periodo;

    public Agenda(Pet pet, List<Servico> servicos, ValorMonetario valorDeslocamento, Periodo periodo) {
        if (servicos == null || servicos.isEmpty()) {
            throw new IllegalArgumentException("A lista de serviços não pode ser vazia.");
        }
        this.pet = pet;
        this.servicos = servicos;
        this.valorDeslocamento = valorDeslocamento;
        this.periodo = periodo;
    }

    public Agenda() {}

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public ValorMonetario getValorDeslocamento() {
        return valorDeslocamento;
    }

    public void setValorDeslocamento(ValorMonetario valorDeslocamento) {
        this.valorDeslocamento = valorDeslocamento;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
}
