package codifica.eleve.application.usecase.pet;

import codifica.eleve.domain.pet.Pet;
import codifica.eleve.domain.pet.PetRepository;

public class CreatePetUseCase {
    private final PetRepository petRepository;

    public CreatePetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet execute(Pet pet) {
        if (petRepository.existsByNomeAndClienteId(pet.getNome(), pet.getCliente().getId())) {
            throw new RuntimeException("Impossível cadastrar dois pets com dados iguais.");
        }
        return petRepository.save(pet);
    }
}