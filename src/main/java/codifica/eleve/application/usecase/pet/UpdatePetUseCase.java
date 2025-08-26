package codifica.eleve.application.usecase.pet;

import codifica.eleve.domain.pet.Pet;
import codifica.eleve.domain.pet.PetRepository;

public class UpdatePetUseCase {
    private final PetRepository petRepository;

    public UpdatePetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet execute(Integer id, Pet pet) {
        if (!petRepository.existsById(id)) {
            throw new RuntimeException("Pet não encontrado.");
        }
        pet.setId(id);
        return petRepository.save(pet);
    }
}