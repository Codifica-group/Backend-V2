package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import java.util.List;

public class ListAgendaUseCase {

    private final AgendaRepository agendaRepository;

    public ListAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public List<Agenda> execute() {
        return agendaRepository.findAll();
    }
}
