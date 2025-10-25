package codifica.eleve.core.application.usecase.despesa;

import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.despesa.DespesaRepository;
import codifica.eleve.core.domain.shared.Pagina;

import java.util.List;

public class ListDespesaUseCase {
    private final DespesaRepository despesaRepository;

    public ListDespesaUseCase(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public Pagina<Despesa> execute(int offset, int size) {
        List<Despesa> despesas = despesaRepository.findAll(offset, size);
        long totalItens = despesaRepository.countAll();
        int totalPaginas = (int) Math.ceil((double) totalItens / size);

        return new Pagina<>(despesas, totalPaginas);
    }
}
