package codifica.eleve.core.application.usecase.raca;

import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteRacaUseCase {
    private final RacaRepository racaRepository;

    public DeleteRacaUseCase(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    public void execute(Integer id) {
        if (!racaRepository.existsById(id)) {
            throw new NotFoundException("Raça não encontrada.");
        }

        racaRepository.deleteById(id);
    }
}