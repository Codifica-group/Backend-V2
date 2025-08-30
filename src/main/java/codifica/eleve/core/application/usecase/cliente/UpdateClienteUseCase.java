package codifica.eleve.core.application.usecase.cliente;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class UpdateClienteUseCase {

    private final ClienteRepository clienteRepository;

    public UpdateClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public String execute(Integer id, Cliente cliente) {
        if (clienteRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Cliente não encontrado.");
        }

        cliente.setId(new Id(id));
        clienteRepository.save(cliente);

        return "Cliente atualizado com sucesso!";
    }
}
