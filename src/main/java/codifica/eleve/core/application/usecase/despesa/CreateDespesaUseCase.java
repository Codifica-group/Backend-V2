package codifica.eleve.core.application.usecase.despesa;

import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.despesa.DespesaRepository;

import java.util.HashMap;
import java.util.Map;

public class CreateDespesaUseCase {
    private final DespesaRepository despesaRepository;

    public CreateDespesaUseCase(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public Map<String, Object> execute(Despesa despesa) {
        Despesa novaDespesa = despesaRepository.save(despesa);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Despesa cadastrada com sucesso.");
        response.put("id", novaDespesa.getId().getValue());
        return response;
    }
}
