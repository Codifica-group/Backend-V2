package codifica.eleve.core.domain.solicitacao;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoAgendaRepository {
    SolicitacaoAgenda save(SolicitacaoAgenda solicitacaoAgenda);
    Optional<SolicitacaoAgenda> findById(Integer id);
    List<SolicitacaoAgenda> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
}
