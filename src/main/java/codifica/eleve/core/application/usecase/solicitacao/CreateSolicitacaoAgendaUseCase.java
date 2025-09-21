package codifica.eleve.core.application.usecase.solicitacao;


import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;

import java.util.HashMap;
import java.util.Map;

public class CreateSolicitacaoAgendaUseCase {

    private final SolicitacaoAgendaRepository repository;

    public CreateSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> execute(SolicitacaoAgenda solicitacaoAgenda) {
        SolicitacaoAgenda solicitacao = repository.save(solicitacaoAgenda);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Solicitação cadastrada com sucesso.");
        response.put("id", solicitacao.getId().getValue());
        return response;
    }
}
