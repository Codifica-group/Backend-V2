package codifica.eleve.infrastructure.persistence.agenda;

import codifica.eleve.infrastructure.persistence.agenda.agendaServico.AgendaServicoEntity;
import codifica.eleve.infrastructure.persistence.pet.PetEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agenda")
public class AgendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private PetEntity pet;

    @OneToMany(
            mappedBy = "agenda",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AgendaServicoEntity> servicos = new ArrayList<>();

    private BigDecimal valorDeslocamento;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    public AgendaEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PetEntity getPet() {
        return pet;
    }

    public void setPet(PetEntity pet) {
        this.pet = pet;
    }

    public List<AgendaServicoEntity> getServicos() {
        return servicos;
    }

    public void setServicos(List<AgendaServicoEntity> servicos) {
        this.servicos = servicos;
    }

    public BigDecimal getValorDeslocamento() {
        return valorDeslocamento;
    }

    public void setValorDeslocamento(BigDecimal valorDeslocamento) {
        this.valorDeslocamento = valorDeslocamento;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
}
