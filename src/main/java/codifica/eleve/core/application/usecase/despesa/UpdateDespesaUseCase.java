package codifica.eleve.core.application.usecase.despesa;

import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.despesa.DespesaRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class UpdateDespesaUseCase {
    private final DespesaRepository despesaRepository;

    public UpdateDespesaUseCase(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public String execute(Integer id, Despesa despesa) {
        if (!despesaRepository.existsById(id)) {
            throw new NotFoundException("Despesa não encontrada.");
        }

        despesa.setId(new Id(id));
        despesaRepository.save(despesa);
        return "Despesa atualizada com sucesso.";
    }
}
