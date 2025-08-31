package codifica.eleve.core.domain.servico;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository {
    Servico save(Servico servico);
    Optional<Servico> findById(Integer id);
    List<Servico> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
    boolean existsByNome(String nome);
}
