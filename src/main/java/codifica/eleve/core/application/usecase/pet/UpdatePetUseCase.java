package codifica.eleve.core.application.usecase.pet;

import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class UpdatePetUseCase {
    private final PetRepository petRepository;

    public UpdatePetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public String execute(Integer id, Pet pet) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet não encontrado.");
        }

        pet.setId(new Id(id));
        return "Pet atualizado com sucesso.";
    }
}