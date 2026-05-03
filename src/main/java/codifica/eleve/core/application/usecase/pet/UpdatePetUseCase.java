package codifica.eleve.core.application.usecase.pet;

import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class UpdatePetUseCase {
    private final PetRepository petRepository;

    public UpdatePetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public String execute(Integer id, Pet pet) {
        validarCamposObrigatorios(pet);

        Pet petExistente = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado."));

        petExistente.setNome(pet.getNome());
        petExistente.setSexo(pet.getSexo());
        petExistente.setFoto(pet.getFoto());
        petExistente.setRaca(pet.getRaca());
        petExistente.setCliente(pet.getCliente());
        petExistente.setPorte(pet.getPorte());
        petRepository.save(petExistente);
        return "Pet atualizado com sucesso.";
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
