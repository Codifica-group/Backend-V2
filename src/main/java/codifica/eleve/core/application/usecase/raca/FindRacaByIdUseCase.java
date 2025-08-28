package codifica.eleve.core.application.usecase.raca;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindRacaByIdUseCase {
    private final RacaRepository racaRepository;

    public FindRacaByIdUseCase(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    public Raca execute(Integer id) {
        return racaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Raça não encontrada."));
    }
}