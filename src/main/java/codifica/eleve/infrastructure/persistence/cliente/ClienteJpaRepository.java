package codifica.eleve.infrastructure.persistence.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Integer> {
    boolean existsByNomeAndTelefone(String nome, String telefone);
    Optional<ClienteEntity> findByNome(String nome);
}
