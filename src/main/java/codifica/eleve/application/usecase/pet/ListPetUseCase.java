package codifica.eleve.application.usecase.pet;

import codifica.eleve.domain.pet.Pet;
import codifica.eleve.domain.pet.PetRepository;
import java.util.List;

public class ListPetUseCase {
    private final PetRepository petRepository;

    public ListPetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> execute() {
        return petRepository.findAll();
    }
}