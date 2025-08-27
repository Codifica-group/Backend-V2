package codifica.eleve.application.usecase.pet;

import codifica.eleve.domain.pet.Pet;
import codifica.eleve.domain.pet.PetRepository;

public class FindPetByIdUseCase {
    private final PetRepository petRepository;

    public FindPetByIdUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet execute(Integer id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado."));
    }
}