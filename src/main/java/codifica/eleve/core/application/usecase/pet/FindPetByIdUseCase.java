package codifica.eleve.core.application.usecase.pet;

import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindPetByIdUseCase {
    private final PetRepository petRepository;

    public FindPetByIdUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet execute(Integer id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado."));
    }
}