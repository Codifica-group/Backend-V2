package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindAgendaByIdUseCase {

    private final AgendaRepository agendaRepository;

    public FindAgendaByIdUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Agenda execute(Integer id) {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agenda não encontrada."));
    }
}
