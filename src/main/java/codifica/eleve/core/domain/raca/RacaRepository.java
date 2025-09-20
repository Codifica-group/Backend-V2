package codifica.eleve.core.domain.raca;

import java.util.List;
import java.util.Optional;

public interface RacaRepository {
    Raca save(Raca raca);
    Optional<Raca> findById(Integer id);
    List<Raca> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
    boolean existsByNome(String nome);
    Optional<Raca> findByNome(String nome);
}
