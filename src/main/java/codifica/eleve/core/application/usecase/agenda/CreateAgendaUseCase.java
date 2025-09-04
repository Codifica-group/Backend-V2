package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import java.util.HashMap;
import java.util.Map;

public class CreateAgendaUseCase {

    private final AgendaRepository agendaRepository;

    public CreateAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Map<String, Object> execute(Agenda agenda) {
        if (!agendaRepository.findConflitos(agenda.getPeriodo()).isEmpty()) {
            throw new ConflictException("Já existe um agendamento para este período.");
        }

        Agenda novaAgenda = agendaRepository.save(agenda);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Agenda cadastrada com sucesso.");
        response.put("id", novaAgenda.getId().getValue());
        return response;
    }
}
