package codifica.eleve.infrastructure.persistence.raca.porte;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorteJpaRepository extends JpaRepository<PorteEntity, Integer> {

}
