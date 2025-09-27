package codifica.eleve.core.application.usecase.agenda.calculadora;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.despesa.DespesaRepository;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import codifica.eleve.interfaces.dto.LucroDTO;

import java.math.BigDecimal;
import java.util.List;

public class CalcularLucroUseCase {

    private final AgendaRepository agendaRepository;
    private final DespesaRepository despesaRepository;

    public CalcularLucroUseCase(AgendaRepository agendaRepository, DespesaRepository despesaRepository) {
        this.agendaRepository = agendaRepository;
        this.despesaRepository = despesaRepository;
    }

    public LucroDTO execute(Periodo periodo) {
        if (periodo.getInicio().isAfter(periodo.getFim())) {
            throw new IllegalArgumentException("A data de início deve ser anterior ou igual à data de fim.");
        }

        List<Agenda> agendasNoPeriodo = agendaRepository.findConflitos(periodo);

        BigDecimal totalGanhos = agendasNoPeriodo.stream()
                .flatMap(agenda -> agenda.getServicos().stream())
                .map(servico -> servico.getValor().getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Despesa> despesasNoPeriodo = despesaRepository.findByPeriodo(periodo);

        BigDecimal totalGastos = despesasNoPeriodo.stream()
                .map(despesa -> despesa.getValor().getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal lucro = totalGanhos.subtract(totalGastos);

        return new LucroDTO(lucro, totalGanhos, totalGastos);
    }
}
