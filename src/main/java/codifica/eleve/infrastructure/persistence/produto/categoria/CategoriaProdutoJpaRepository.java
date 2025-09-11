package codifica.eleve.infrastructure.persistence.produto.categoria;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProdutoJpaRepository extends JpaRepository<CategoriaProdutoEntity, Integer> {
    boolean existsByNome(String nome);
}
