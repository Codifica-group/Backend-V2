package codifica.eleve.core.application.usecase.cliente;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.shared.Pagina;

import java.util.List;

public class ListClienteUseCase {

    private final ClienteRepository clienteRepository;

    public ListClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Pagina<Cliente> execute(int offset, int size) {
        List<Cliente> clientes = clienteRepository.findAll(offset, size);
        long totalItens = clienteRepository.countAll();
        int totalPaginas = (int) Math.ceil((double) totalItens / size);

        return new Pagina<>(clientes, totalPaginas);
    }
}
