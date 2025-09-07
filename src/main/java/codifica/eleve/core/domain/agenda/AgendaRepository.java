package codifica.eleve.core.domain.agenda;

import codifica.eleve.core.domain.shared.Periodo;
import java.util.List;
import java.util.Optional;

public interface AgendaRepository {
    Agenda save(Agenda agenda);
    Optional<Agenda> findById(Integer id);
    List<Agenda> findAll();
    List<Agenda> findConflitos(Periodo periodo);
    List<Agenda> findConflitosExcluindoId(Integer id, Periodo periodo);
    void deleteById(Integer id);
}
