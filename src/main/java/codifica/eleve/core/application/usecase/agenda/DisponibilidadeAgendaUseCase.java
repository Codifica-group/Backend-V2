package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.shared.DiaDaSemana;
import codifica.eleve.core.domain.shared.Periodo;
import org.springframework.beans.factory.annotation.Value;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

    public List<Map<String, Object>> findDiasDisponiveis(Periodo periodo) {
        LocalDate startDate = periodo.getInicio().toLocalDate();
        LocalDate endDate = periodo.getFim().toLocalDate();

        return startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> {
                    DayOfWeek dayOfWeek = date.getDayOfWeek();
                    if (dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.MONDAY) {
                        return false;
                    }
                    return !findHorariosDisponiveis(date).isEmpty();
                })
                .map(date -> {
                    Map<String, Object> diaInfo = new HashMap<>();
                    diaInfo.put("data", date);
                    diaInfo.put("diaSemana", DiaDaSemana.fromDate(date));
                    return diaInfo;
                })
                .collect(Collectors.toList());
    }

    public List<LocalTime> findHorariosDisponiveis(LocalDate dia) {
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
        while (horarioAtual.isBefore(fimExpediente)) {
            if (!horariosOcupados.contains(horarioAtual)) {
                horariosDisponiveis.add(horarioAtual);
            }
            horarioAtual = horarioAtual.plusHours(duracaoMinimaAgendamentoHoras);
        }

        return horariosDisponiveis;
    }
}
