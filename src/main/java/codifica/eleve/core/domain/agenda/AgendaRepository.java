package codifica.eleve.core.domain.agenda;

import codifica.eleve.core.domain.shared.Periodo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendaRepository {
    Agenda save(Agenda agenda);
    Optional<Agenda> findById(Integer id);
    List<Agenda> findAll(int offset, int size);
    List<Agenda> findConflitos(Periodo periodo);
    List<Agenda> findConflitosExcluindoId(Integer id, Periodo periodo);
    void deleteById(Integer id);
    List<Agenda> findByFilter(Filtro filtro, int offset, int size);
    boolean existsByPetId(Integer petId);
    boolean existsByServicoId(Integer servicoId);
    List<Agenda> findConflitosByDataHoraInicio(LocalDateTime dataHoraInicio);
    Optional<Agenda> findFutureByPetId(Integer petId, LocalDateTime now);
    long countAll();
    long countByFilter(Filtro filtro);
}
