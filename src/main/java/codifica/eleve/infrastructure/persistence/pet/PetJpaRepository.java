package codifica.eleve.infrastructure.persistence.pet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetJpaRepository extends JpaRepository<PetEntity, Integer> {
    List<PetEntity> findByClienteId(Integer clienteId);
    boolean existsByNomeAndClienteId(String nome, Integer clienteId);
    boolean existsByRacaId(Integer racaId);
    boolean existsByClienteId(Integer clienteId);
}
