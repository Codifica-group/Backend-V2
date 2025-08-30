package codifica.eleve.core.application.usecase.cliente;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindClienteByIdUseCase {

    private final ClienteRepository clienteRepository;

    public FindClienteByIdUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente execute(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado."));
    }
}
