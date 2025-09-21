package codifica.eleve.core.application.usecase.solicitacao;

import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;

import java.util.HashMap;
import java.util.Map;

public class UpdateSolicitacaoAgendaUseCase {

    private final SolicitacaoAgendaRepository repository;

    public UpdateSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> execute(Integer id, SolicitacaoAgenda solicitacaoAgenda) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Solicitação de agenda não encontrada.");
        }

        solicitacaoAgenda.setId(new Id(id));
        SolicitacaoAgenda solicitacao = repository.save(solicitacaoAgenda);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Solicitação atualizada com sucesso.");
        response.put("id", solicitacao.getId().getValue());
        return response;
    }
}
