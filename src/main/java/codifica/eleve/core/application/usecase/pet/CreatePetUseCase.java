package codifica.eleve.core.application.usecase.pet;

import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.pet.Pet;

import java.util.HashMap;

public class CreatePetUseCase {
    private final PetRepository petRepository;

    public CreatePetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Object execute(Pet pet) {
        if (petRepository.existsByNomeAndClienteId(pet.getNome(), pet.getCliente().getId())) {
            throw new ConflictException("Impossível cadastrar dois pets com dados iguais.");
        }

        petRepository.save(pet);

        var response = new HashMap<String, Object>();
        response.put("mensagem", "Pet cadastrado com sucesso.");
        response.put("id", pet.getId());
        return response;
    }
}