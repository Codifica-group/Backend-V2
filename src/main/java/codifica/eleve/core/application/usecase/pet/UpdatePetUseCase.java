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
        Pet petExistente = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado."));

        petExistente.setNome(pet.getNome());
        petExistente.setRaca(pet.getRaca());
        petExistente.setCliente(pet.getCliente());
        petRepository.save(petExistente);
        return "Pet atualizado com sucesso.";
    }
}
