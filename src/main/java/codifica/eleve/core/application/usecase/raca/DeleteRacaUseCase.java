package codifica.eleve.core.application.usecase.raca;

import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteRacaUseCase {
    private final RacaRepository racaRepository;
    private final PetRepository petRepository;

    public DeleteRacaUseCase(RacaRepository racaRepository, PetRepository petRepository) {
        this.racaRepository = racaRepository;
        this.petRepository = petRepository;
    }

    public void execute(Integer id) {
        if (!racaRepository.existsById(id)) {
            throw new NotFoundException("Raça não encontrada.");
        }

        if (petRepository.existsByRacaId(id)) {
            throw new ConflictException("Não é possível deletar raças que possuem pets cadastrados.");
        }

        racaRepository.deleteById(id);
    }
}
