package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteAgendaUseCase {

    private final AgendaRepository agendaRepository;

    public DeleteAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public void execute(Integer id) {
        agendaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agenda não encontrada."));

        agendaRepository.deleteById(id);
    }
}
