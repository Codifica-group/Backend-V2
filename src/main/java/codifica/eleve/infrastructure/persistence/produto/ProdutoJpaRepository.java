package codifica.eleve.infrastructure.persistence.produto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoJpaRepository extends JpaRepository<ProdutoEntity, Integer> {
    boolean existsByCategoriaProdutoIdAndNome(Integer categoriaId, String nome);
    boolean existsByCategoriaProdutoId(Integer categoriaId);
}
