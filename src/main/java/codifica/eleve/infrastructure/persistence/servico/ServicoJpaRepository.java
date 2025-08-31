package codifica.eleve.infrastructure.persistence.servico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoJpaRepository extends JpaRepository<ServicoEntity, Integer> {
    boolean existsByNome(String nome);
}
