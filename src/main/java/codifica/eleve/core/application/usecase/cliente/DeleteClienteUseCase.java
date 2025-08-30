package codifica.eleve.core.application.usecase.cliente;

import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteClienteUseCase {

    private final ClienteRepository clienteRepository;

    public DeleteClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void execute(Integer id) {
        if (clienteRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Cliente não encontrado.");
        }

        clienteRepository.deleteById(id);
    }
}
