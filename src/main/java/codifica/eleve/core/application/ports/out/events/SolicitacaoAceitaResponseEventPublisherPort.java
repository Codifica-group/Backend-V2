package codifica.eleve.core.application.ports.out.events;

import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAceitaResponseEvent;

public interface SolicitacaoAceitaResponseEventPublisherPort {
    void publishSolicitacaoAceitaResponse(SolicitacaoAceitaResponseEvent event);
}
