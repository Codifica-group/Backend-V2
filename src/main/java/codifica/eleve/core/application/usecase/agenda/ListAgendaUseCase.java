package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.shared.Pagina;

import java.util.List;

public class ListAgendaUseCase {

    private final AgendaRepository agendaRepository;

    public ListAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Pagina<Agenda> execute(int offset, int size) {
        List<Agenda> agendas = agendaRepository.findAll(offset, size);
        long totalItens = agendaRepository.countAll();
        int totalPaginas = (int) Math.ceil((double) totalItens / size);

        return new Pagina<>(agendas, totalPaginas);
    }
}
