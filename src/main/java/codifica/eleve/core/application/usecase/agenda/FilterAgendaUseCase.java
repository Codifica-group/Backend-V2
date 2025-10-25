package codifica.eleve.core.application.usecase.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.agenda.Filtro;
import codifica.eleve.core.domain.shared.Pagina;

import java.util.List;

public class FilterAgendaUseCase {

    private final AgendaRepository agendaRepository;

    public FilterAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Pagina<Agenda> execute(Filtro filtro, int offset, int size) {
        List<Agenda> agendas = agendaRepository.findByFilter(filtro, offset, size);
        long totalItens = agendaRepository.countByFilter(filtro);
        int totalPaginas = (int) Math.ceil((double) totalItens / size);

        return new Pagina<>(agendas, totalPaginas);
    }
}
