package codifica.eleve.infrastructure.persistence.despesa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DespesaJpaRepository extends JpaRepository<DespesaEntity, Integer> {
    boolean existsByProdutoId(Integer produtoId);
    List<DespesaEntity> findByDataBetween(LocalDate start, LocalDate end);
}
