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

    @Query("SELECT a FROM AgendaEntity a " +
            "LEFT JOIN a.pet p " +
            "LEFT JOIN p.cliente c " +
            "LEFT JOIN p.raca r " +
            "WHERE (:dataInicio IS NULL OR a.dataHoraInicio >= :dataInicio) " +
            "AND (:dataFim IS NULL OR a.dataHoraFim <= :dataFim) " +
            "AND (:clienteId IS NULL OR c.id = :clienteId) " +
            "AND (:petId IS NULL OR p.id = :petId) " +
            "AND (:racaId IS NULL OR r.id = :racaId) " +
            "AND (:servicoId IS NULL OR " +
            "    (SELECT COUNT(s.id) FROM AgendaServicoEntity aserv JOIN aserv.servico s WHERE aserv.agenda.id = a.id AND s.id IN :servicoId) = :servicoIdSize AND " +
            "    (SELECT COUNT(aserv2.id) FROM AgendaServicoEntity aserv2 WHERE aserv2.agenda.id = a.id) = :servicoIdSize" +
            ")")
    List<AgendaEntity> findByFilter(@Param("dataInicio") LocalDateTime dataInicio,
                                    @Param("dataFim") LocalDateTime dataFim,
                                    @Param("clienteId") Integer clienteId,
                                    @Param("petId") Integer petId,
                                    @Param("racaId") Integer racaId,
                                    @Param("servicoId") List<Integer> servicoId,
                                    @Param("servicoIdSize") Long servicoIdSize);
}
