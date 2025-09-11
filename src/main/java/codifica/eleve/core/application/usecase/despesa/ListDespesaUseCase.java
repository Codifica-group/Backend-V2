package codifica.eleve.core.application.usecase.despesa;

import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.despesa.DespesaRepository;
import java.util.List;

public class ListDespesaUseCase {
    private final DespesaRepository despesaRepository;

    public ListDespesaUseCase(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public List<Despesa> execute() {
        return despesaRepository.findAll();
    }
}
