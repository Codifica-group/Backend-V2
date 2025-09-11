package codifica.eleve.infrastructure.persistence.despesa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaJpaRepository extends JpaRepository<DespesaEntity, Integer> {
    boolean existsByProdutoId(Integer produtoId);
}
