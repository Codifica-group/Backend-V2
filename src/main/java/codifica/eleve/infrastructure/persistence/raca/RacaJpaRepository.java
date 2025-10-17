package codifica.eleve.infrastructure.persistence.raca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RacaJpaRepository extends JpaRepository<RacaEntity, Integer> {
    boolean existsByNome(String nome);

    @Query("SELECT r FROM RacaEntity r WHERE lower(replace(r.nome, ' ', '')) = lower(replace(:nome, ' ', ''))")
    Optional<RacaEntity> findByNomeRemovingSpacesAndIgnoringCase(@Param("nome") String nome);
}
