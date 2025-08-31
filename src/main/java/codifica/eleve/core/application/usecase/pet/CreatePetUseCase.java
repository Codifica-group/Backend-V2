package codifica.eleve.core.application.usecase.pet;

import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.pet.Pet;

import java.util.HashMap;
import java.util.Map;

public class CreatePetUseCase {
    private final PetRepository petRepository;

    public CreatePetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Map<String, Object> execute(Pet pet) {
        if (petRepository.existsByNomeAndClienteId(pet.getNome(), pet.getCliente().getId().getValue())) {
            throw new ConflictException("Impossível cadastrar dois pets com dados iguais.");
        }

        Pet novoPet = petRepository.save(pet);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Pet cadastrado com sucesso.");
        response.put("id", novoPet.getId().getValue());
        return response;
    }
}
