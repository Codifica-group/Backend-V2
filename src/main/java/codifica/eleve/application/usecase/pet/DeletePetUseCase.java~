package codifica.eleve.application.usecase.pet;

import codifica.eleve.domain.pet.PetRepository;

public class DeletePetUseCase {
    private final PetRepository petRepository;
    // private final AgendaRepository agendaRepository;

    public DeletePetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public void execute(Integer id) {
        if (!petRepository.existsById(id)) {
            throw new RuntimeException("Pet não encontrado.");
        }
        // if (agendaRepository.existsByPetId(id)) {
        //     throw new RuntimeException("Não é possível deletar pets que possuem agendas associadas.");
        // }
        petRepository.deleteById(id);
    }
}