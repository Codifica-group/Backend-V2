package codifica.eleve.core.application.usecase.solicitacao;

import codifica.eleve.core.domain.shared.Pagina;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;

import java.util.List;

public class ListSolicitacaoAgendaUseCase {

    private final SolicitacaoAgendaRepository repository;

    public ListSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository) {
        this.repository = repository;
    }

    public Pagina<SolicitacaoAgenda> execute(int offset, int size) {
        List<SolicitacaoAgenda> solicitacoes = repository.findAll(offset, size); // TODO: implementar filtro>
        long totalItens = repository.countAll();
        int totalPaginas = (int) Math.ceil((double) totalItens / size);

        return new Pagina<>(solicitacoes, totalPaginas);
    }
}
