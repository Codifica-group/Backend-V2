package codifica.eleve.infrastructure.persistence.raca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RacaExternaRepository extends JpaRepository<RacaExternaEntity, Long> {
    Optional<RacaExternaEntity> findByNomeIgnoreCase(String nome);
    Optional<RacaExternaEntity> findByRacaIdExterno(Integer racaIdExterno);
}
