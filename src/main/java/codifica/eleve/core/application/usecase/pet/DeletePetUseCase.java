package codifica.eleve.core.application.usecase.pet;

import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeletePetUseCase {
    private final PetRepository petRepository;
    private final AgendaRepository agendaRepository;

    public DeletePetUseCase(PetRepository petRepository, AgendaRepository agendaRepository) {
        this.petRepository = petRepository;
        this.agendaRepository = agendaRepository;
    }

    public void execute(Integer id) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet não encontrado.");
        }

        if (agendaRepository.existsByPetId(id)) {
            throw new ConflictException("Não é possível deletar pets que possuem agendas associadas.");
        }

        petRepository.deleteById(id);
    }
}
