package codifica.eleve.infrastructure.persistence.solicitacao.agenda_servico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitacaoAgendaServicoJpaRepository extends JpaRepository<SolicitacaoAgendaServicoEntity, Integer> { // Corrected Entity

}
