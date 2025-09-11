package codifica.eleve.core.domain.despesa;

import java.util.List;
import java.util.Optional;

public interface DespesaRepository {
    Despesa save(Despesa despesa);
    Optional<Despesa> findById(Integer id);
    List<Despesa> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
    boolean existsByProdutoId(Integer produtoId);
}
