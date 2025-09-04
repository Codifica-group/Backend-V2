package codifica.eleve.core.domain.agenda;

import codifica.eleve.core.domain.shared.Periodo;
import java.util.List;
import java.util.Optional;

public interface AgendaRepository {
    Agenda save(Agenda agenda);
    Optional<Agenda> findById(Integer id);
    List<Agenda> findConflitos(Periodo periodo);
}
