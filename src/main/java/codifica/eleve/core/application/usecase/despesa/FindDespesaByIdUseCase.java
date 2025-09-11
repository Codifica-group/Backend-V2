package codifica.eleve.core.application.usecase.despesa;

import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.despesa.DespesaRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindDespesaByIdUseCase {
    private final DespesaRepository despesaRepository;

    public FindDespesaByIdUseCase(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public Despesa execute(Integer id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Despesa não encontrada."));
    }
}
