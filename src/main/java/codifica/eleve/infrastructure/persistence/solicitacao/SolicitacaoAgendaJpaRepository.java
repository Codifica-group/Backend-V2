package codifica.eleve.infrastructure.persistence.solicitacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitacaoAgendaJpaRepository extends JpaRepository<SolicitacaoAgendaEntity, Integer> {

}
