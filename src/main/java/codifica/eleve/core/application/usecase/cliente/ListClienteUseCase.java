package codifica.eleve.core.application.usecase.cliente;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;

import java.util.List;

public class ListClienteUseCase {

    private final ClienteRepository clienteRepository;

    public ListClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> execute() {
        return clienteRepository.findAll();
    }
}
