package codifica.eleve.infrastructure.persistence.raca;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RacaJpaRepository extends JpaRepository<RacaEntity, Integer> {
    boolean existsByNome(String nome);
    Optional<RacaEntity> findByNome(String nome);
}
