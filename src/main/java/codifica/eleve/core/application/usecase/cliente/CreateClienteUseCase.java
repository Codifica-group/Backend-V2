package codifica.eleve.core.application.usecase.cliente;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;

import java.util.HashMap;
import java.util.Map;

public class CreateClienteUseCase {

    private final ClienteRepository clienteRepository;

    public CreateClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Map<String, Object> execute(Cliente cliente) {
        if (clienteRepository.existsByNomeAndTelefone(cliente.getNome(), cliente.getTelefone().getNumero())) {
            throw new ConflictException("Impossível cadastrar dois clientes com os mesmos dados.");
        }

        Cliente novoCliente = clienteRepository.save(cliente);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Cliente cadastrado com sucesso.");
        response.put("id", novoCliente.getId().getValue());
        return response;
    }
}
