package codifica.eleve.core.application.usecase.solicitacao;


import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;

import java.util.HashMap;
import java.util.Map;

public class CreateSolicitacaoAgendaUseCase {

    private final SolicitacaoAgendaRepository repository;
    private final AgendaRepository agendaRepository;

    public CreateSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository, AgendaRepository agendaRepository) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
    }

    public Map<String, Object> execute(SolicitacaoAgenda solicitacaoAgenda) {
        if (!agendaRepository.findConflitosByDataHoraInicio(solicitacaoAgenda.getDataHoraInicio()).isEmpty()) {
            throw new ConflictException("Já existe um agendamento para este período.");
        }

        SolicitacaoAgenda solicitacao = repository.save(solicitacaoAgenda);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Solicitação cadastrada com sucesso.");
        response.put("id", solicitacao.getId().getValue());
        return response;
    }
}
