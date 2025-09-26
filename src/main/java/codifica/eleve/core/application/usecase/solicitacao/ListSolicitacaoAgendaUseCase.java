package codifica.eleve.core.application.usecase.solicitacao;

import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;

import java.util.List;

public class ListSolicitacaoAgendaUseCase {

    private final SolicitacaoAgendaRepository repository;

    public ListSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository) {
        this.repository = repository;
    }

    public List<SolicitacaoAgenda> execute(int offset, int size) {
        return repository.findAll(offset, size);
    }
}
