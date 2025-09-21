package codifica.eleve.core.domain.solicitacao;

import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;

import java.time.LocalDateTime;
import java.util.List;

public class SolicitacaoAgenda {

    private Id id;
    private Pet pet;
    private List<Servico> servicos;
    private ValorMonetario valorDeslocamento;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraSolicitacao;
    private String status;

    public SolicitacaoAgenda(Pet pet, List<Servico> servicos, ValorMonetario valorDeslocamento, LocalDateTime inicio, LocalDateTime dataHoraSolicitacao, String status) {
        if (servicos == null || servicos.isEmpty()) {
            throw new IllegalArgumentException("A lista de serviços não pode ser vazia.");
        }
        if (inicio == null || dataHoraSolicitacao == null) {
            throw new IllegalArgumentException("As datas de início e solicitação não podem ser nulas.");
        }
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("O status da solicitação não pode ser vazio.");
        }
        this.pet = pet;
        this.servicos = servicos;
        this.valorDeslocamento = valorDeslocamento;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraSolicitacao = dataHoraSolicitacao;
        this.status = status;
    }

    public SolicitacaoAgenda() {}

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

    public ValorMonetario getValorDeslocamento() {
        return valorDeslocamento;
    }

    public void setValorDeslocamento(ValorMonetario valorDeslocamento) {
        this.valorDeslocamento = valorDeslocamento;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
}
