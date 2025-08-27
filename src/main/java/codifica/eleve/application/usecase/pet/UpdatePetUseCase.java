package codifica.eleve.application.usecase.pet;

import codifica.eleve.domain.pet.Pet;
import codifica.eleve.domain.pet.PetRepository;
import codifica.eleve.domain.shared.exceptions.NotFoundException;

public class UpdatePetUseCase {
    private final PetRepository petRepository;

    public UpdatePetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public String execute(Integer id, Pet pet) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet não encontrado.");
        }

        pet.setId(id);
        return "Pet atualizado com sucesso.";
    }
}