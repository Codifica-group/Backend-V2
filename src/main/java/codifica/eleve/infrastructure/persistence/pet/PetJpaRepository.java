package codifica.eleve.infrastructure.persistence.pet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetJpaRepository extends JpaRepository<PetEntity, Integer> {
    boolean existsByNomeAndClienteId(String nome, Integer clienteId);
    boolean existsByRacaId(Integer racaId);
    boolean existsByClienteId(Integer clienteId);
}
