package codifica.eleve.application.usecase.pet;

import codifica.eleve.domain.pet.Pet;
import codifica.eleve.domain.pet.PetRepository;
import codifica.eleve.domain.shared.exceptions.ConflictException;

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

        var resposta = new HashMap<String, Object>();
        resposta.put("mensagem", "Pet cadastrado com sucesso.");
        resposta.put("id", pet.getId());
        return resposta;
    }
}