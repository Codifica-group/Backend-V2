package codifica.eleve.core.application.usecase.pet;

import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import codifica.eleve.core.domain.pet.Pet;

import java.util.HashMap;
import java.util.Map;

public class CreatePetUseCase {
    private final PetRepository petRepository;

    public CreatePetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Map<String, Object> execute(Pet pet) {
        validarCamposObrigatorios(pet);

        if (petRepository.existsByNomeAndClienteId(pet.getNome(), pet.getCliente().getId().getValue())) {
            throw new ConflictException("Impossível cadastrar dois pets com dados iguais.");
        }

        Pet novoPet = petRepository.save(pet);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Pet cadastrado com sucesso.");
        response.put("id", novoPet.getId().getValue());
        response.put("nome", novoPet.getNome());
        response.put("sexo", novoPet.getSexo());
        response.put("foto", novoPet.getFoto());
        response.put("porte", novoPet.getPorte() != null ? novoPet.getPorte().getNome() : null);
        response.put("raca", novoPet.getRaca() != null ? novoPet.getRaca().getNome() : null);
        return response;
    }

    private void validarCamposObrigatorios(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Dados do pet não foram informados.");
        }

        if (pet.getCliente() == null || pet.getCliente().getId() == null) {
            throw new IllegalArgumentException("Campo obrigatório ausente: clienteId.");
        }

        if (pet.getRaca() == null || pet.getRaca().getId() == null) {
            throw new IllegalArgumentException("Campo obrigatório ausente: racaId.");
        }

        if (pet.getPorte() == null || pet.getPorte().getId() == null) {
            throw new IllegalArgumentException("Campo obrigatório ausente: porteId.");
        }

        if (pet.getSexo() == null || pet.getSexo().trim().isEmpty()) {
            throw new IllegalArgumentException("Campo obrigatório ausente: sexo.");
        }

        if (pet.getFoto() == null || pet.getFoto().trim().isEmpty()) {
            throw new IllegalArgumentException("Campo obrigatório ausente: foto.");
        }
    }
}
