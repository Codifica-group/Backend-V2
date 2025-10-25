package codifica.eleve.interfaces.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DisponibilidadeDTO {
    private LocalDate dia;
    private String diaSemana;
    private List<LocalTime> horarios;

    public DisponibilidadeDTO(LocalDate dia, String diaSemana, List<LocalTime> horarios) {
        this.dia = dia;
        this.diaSemana = diaSemana;
        this.horarios = horarios;
    }

    public LocalDate getDia() {
        return dia;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public List<LocalTime> getHorarios() {
        return horarios;
    }
}
