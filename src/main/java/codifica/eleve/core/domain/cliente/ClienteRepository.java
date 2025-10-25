package codifica.eleve.core.domain.cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Integer id);
    List<Cliente> findAll(int offset, int size);
    void deleteById(Integer id);
    boolean existsById(Integer id);
    boolean existsByNomeAndTelefone(String nome, String telefone);
    long countAll();
}
