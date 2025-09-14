package codifica.eleve.core.application.usecase.cliente;

import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteClienteUseCase {

    private final ClienteRepository clienteRepository;
    private final PetRepository petRepository;

    public DeleteClienteUseCase(ClienteRepository clienteRepository, PetRepository petRepository) {
        this.clienteRepository = clienteRepository;
        this.petRepository = petRepository;
    }

    public void execute(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("Cliente não encontrado.");
        }

        if (petRepository.existsByClienteId(id)) {
            throw new ConflictException("Não é possível deletar clientes que possuem pets cadastrados.");
        }

        clienteRepository.deleteById(id);
    }
}
