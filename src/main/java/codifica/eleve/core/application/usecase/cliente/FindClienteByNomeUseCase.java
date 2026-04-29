package codifica.eleve.core.application.usecase.cliente;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindClienteByNomeUseCase {

    private final ClienteRepository clienteRepository;

    public FindClienteByNomeUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente execute(String nome) {
        return clienteRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado."));
    }
}
