package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public class FindFutureAgendasByPetIdUseCase {

    private final AgendaRepository agendaRepository;

    public FindFutureAgendasByPetIdUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Optional<Agenda> execute(Integer petId) {
        return agendaRepository.findFutureByPetId(petId, LocalDateTime.now());
    }
}
