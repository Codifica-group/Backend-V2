package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.agenda.Filtro;

import java.util.List;

public class FilterAgendaUseCase {

    private final AgendaRepository agendaRepository;

    public FilterAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public List<Agenda> execute(Filtro filtro) {
        return agendaRepository.findByFilter(filtro);
    }
}
