package codifica.eleve.infrastructure.persistence.raca;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RacaJpaRepository extends JpaRepository<RacaEntity, Integer> {
    boolean existsByNome(String nome);
}