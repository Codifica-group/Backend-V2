package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;

public class UpdateAgendaUseCase {

    private final AgendaRepository agendaRepository;

    public UpdateAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Map<String, Object> execute(Integer id, Agenda agendaAtualizada) {
        agendaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agenda não encontrada."));

        if (!agendaRepository.findConflitosExcluindoId(id, agendaAtualizada.getPeriodo()).isEmpty()) {
            throw new ConflictException("Já existe outro agendamento no período informado.");
        }

        agendaAtualizada.setId(new Id(id));
        Agenda agendaSalva = agendaRepository.save(agendaAtualizada);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Agenda atualizada com sucesso.");
        response.put("id", agendaSalva.getId().getValue());
        return response;
    }
}
