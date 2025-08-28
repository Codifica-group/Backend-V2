package codifica.eleve.core.application.usecase.raca;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;

import java.util.HashMap;
import java.util.Map;

public class CreateRacaUseCase {
    private final RacaRepository racaRepository;

    public CreateRacaUseCase(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    public Object execute(Raca raca) {
        if (racaRepository.existsByNome(raca.getNome())) {
            throw new ConflictException("Impossível cadastrar duas raças com o mesmo nome.");
        }

        Raca savedRaca = racaRepository.save(raca);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Raça cadastrada com sucesso.");
        response.put("id", savedRaca.getId());
        return response;
    }
}