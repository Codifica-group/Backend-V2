package codifica.eleve.infrastructure.persistence.agenda;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaJpaRepository extends JpaRepository<AgendaEntity, Integer> {
    @Query("SELECT a FROM AgendaEntity a WHERE NOT (a.dataHoraFim <= :inicio OR a.dataHoraInicio >= :fim)")
    List<AgendaEntity> findConflitos(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT a FROM AgendaEntity a WHERE a.id != :id AND NOT (a.dataHoraFim <= :inicio OR a.dataHoraInicio >= :fim)")
    List<AgendaEntity> findConflitosExcluindoId(@Param("id") Integer id, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
