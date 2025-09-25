package codifica.eleve.core.application.usecase.pet;

import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;

import java.util.List;

public class FindPetsByClienteIdUseCase {
    private final PetRepository petRepository;

    public FindPetsByClienteIdUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> execute(Integer id) {
        return petRepository.findByClienteId(id);
    }
}
