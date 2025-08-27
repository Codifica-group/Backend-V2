package codifica.eleve.application.usecase.pet;

import codifica.eleve.domain.pet.PetRepository;
import codifica.eleve.domain.shared.exceptions.NotFoundException;
import codifica.eleve.domain.shared.exceptions.ConflictException;

public class DeletePetUseCase {
    private final PetRepository petRepository;
    // private final AgendaRepository agendaRepository;

    public DeletePetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public void execute(Integer id) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet não encontrado.");
        }

        // if (agendaRepository.existsByPetId(id)) {
        //     throw new ConflictException("Não é possível deletar pets que possuem agendas associadas.");
        // }

        petRepository.deleteById(id);
    }
}