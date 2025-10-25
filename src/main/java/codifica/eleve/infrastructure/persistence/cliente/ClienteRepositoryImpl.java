package codifica.eleve.infrastructure.persistence.cliente;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.infrastructure.adapters.ClienteMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository clienteJpaRepository;
    private final ClienteMapper clienteMapper;

    public ClienteRepositoryImpl(ClienteJpaRepository clienteJpaRepository, ClienteMapper clienteMapper) {
        this.clienteJpaRepository = clienteJpaRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity clienteEntity = clienteMapper.toEntity(cliente);
        ClienteEntity savedCliente = clienteJpaRepository.save(clienteEntity);
        return clienteMapper.toDomain(savedCliente);
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        return clienteJpaRepository.findById(id).map(clienteMapper::toDomain);
    }

    @Override
    public List<Cliente> findAll(int offset, int size) {
        return clienteJpaRepository.findAll(PageRequest.of(offset, size)).stream().map(clienteMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        clienteJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return clienteJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNomeAndTelefone(String nome, String telefone) {
        return clienteJpaRepository.existsByNomeAndTelefone(nome, telefone);
    }

    @Override
    public long countAll() {
        return clienteJpaRepository.count();
    }
}
