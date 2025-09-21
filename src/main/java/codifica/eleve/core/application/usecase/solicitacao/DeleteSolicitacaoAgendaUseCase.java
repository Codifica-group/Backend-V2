package codifica.eleve.core.application.usecase.solicitacao;

import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;

public class DeleteSolicitacaoAgendaUseCase {

    private final SolicitacaoAgendaRepository repository;

    public DeleteSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository) {
        this.repository = repository;
    }

    public void execute(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Solicitação de agenda não encontrada.");
        }

        repository.deleteById(id);
    }
}
