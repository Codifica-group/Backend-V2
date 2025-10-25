package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.shared.DiaDaSemana;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.interfaces.dto.DisponibilidadeDTO;
import org.springframework.beans.factory.annotation.Value;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DisponibilidadeAgendaUseCase {

    private final AgendaRepository agendaRepository;

    @Value("${HORA_INICIO_EXPEDIENTE}")
    private LocalTime inicioExpediente;

    @Value("${HORA_FIM_EXPEDIENTE}")
    private LocalTime fimExpediente;

    @Value("${DURACAO_MINIMA_AGENDAMENTO_HORAS}")
    private int duracaoMinimaAgendamentoHoras;

    public DisponibilidadeAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public List<DisponibilidadeDTO> findDisponibilidade(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        return startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> {
                    if (date.isEqual(LocalDate.now()) && inicioExpediente.isBefore(startDateTime.toLocalTime())) {
                        return true;
                    }
                    return date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now());
                })
                .map(date -> {
                    List<LocalTime> horariosDisponiveis = findHorariosDisponiveis(date, startDateTime.toLocalTime());
                    if (!horariosDisponiveis.isEmpty()) {
                        return new DisponibilidadeDTO(date, DiaDaSemana.fromDate(date), horariosDisponiveis);
                    }
                    return null;
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    private List<LocalTime> findHorariosDisponiveis(LocalDate dia, LocalTime startOfDayTime) {
        if (dia.getDayOfWeek() == DayOfWeek.SUNDAY || dia.getDayOfWeek() == DayOfWeek.MONDAY) {
            return new ArrayList<>();
        }

        Periodo periodoDoDia = new Periodo(dia.atStartOfDay(), dia.atTime(LocalTime.MAX));
        List<Agenda> agendamentosDoDia = agendaRepository.findConflitos(periodoDoDia);

        List<LocalTime> horariosOcupados = agendamentosDoDia.stream()
                .map(agenda -> agenda.getPeriodo().getInicio().toLocalTime())
                .collect(Collectors.toList());

        List<LocalTime> horariosDisponiveis = new ArrayList<>();
        LocalTime horarioAtual = inicioExpediente;

        if (dia.isEqual(LocalDate.now()) && startOfDayTime.isAfter(horarioAtual)) {
            while (horarioAtual.isBefore(startOfDayTime.plusHours(duracaoMinimaAgendamentoHoras))) {
                horarioAtual = horarioAtual.plusHours(duracaoMinimaAgendamentoHoras);
            }
        }


        while (horarioAtual.isBefore(fimExpediente)) {
            if (!horariosOcupados.contains(horarioAtual)) {
                if (dia.isEqual(LocalDate.now())) {
                    if (!horarioAtual.isBefore(startOfDayTime)) {
                        horariosDisponiveis.add(horarioAtual);
                    }
                } else {
                    horariosDisponiveis.add(horarioAtual);
                }
            }
            horarioAtual = horarioAtual.plusHours(duracaoMinimaAgendamentoHoras);
        }

        return horariosDisponiveis;
    }
}
