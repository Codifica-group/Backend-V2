package codifica.eleve.core.application.usecase.solicitacao;

import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;

public class FindSolicitacaoAgendaByIdUseCase {

    private final SolicitacaoAgendaRepository repository;

    public FindSolicitacaoAgendaByIdUseCase(SolicitacaoAgendaRepository repository) {
        this.repository = repository;
    }

    public SolicitacaoAgenda execute(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação de agenda não encontrada."));
    }
}
