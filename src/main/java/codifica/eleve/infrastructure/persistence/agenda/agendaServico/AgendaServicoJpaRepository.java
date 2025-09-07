package codifica.eleve.infrastructure.persistence.agenda.agendaServico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaServicoJpaRepository extends JpaRepository<AgendaServicoEntity, Integer> {

}
