package codifica.eleve.core.application.usecase.despesa;

import codifica.eleve.core.domain.despesa.DespesaRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteDespesaUseCase {
    private final DespesaRepository despesaRepository;

    public DeleteDespesaUseCase(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public void execute(Integer id) {
        if (!despesaRepository.existsById(id)) {
            throw new NotFoundException("Despesa não encontrada.");
        }

        despesaRepository.deleteById(id);
    }
}
