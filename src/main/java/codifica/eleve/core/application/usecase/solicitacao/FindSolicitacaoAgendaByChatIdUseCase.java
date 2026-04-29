package codifica.eleve.core.application.usecase.solicitacao;

import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;

import java.util.List;

public class FindSolicitacaoAgendaByChatIdUseCase {

    private final SolicitacaoAgendaRepository repository;

    public FindSolicitacaoAgendaByChatIdUseCase(SolicitacaoAgendaRepository repository) {
        this.repository = repository;
    }

    public List<SolicitacaoAgenda> execute(Integer chatId) {
        return repository.findByChatId(chatId);
    }
}
