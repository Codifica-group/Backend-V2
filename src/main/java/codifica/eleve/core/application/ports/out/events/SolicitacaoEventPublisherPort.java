package codifica.eleve.core.application.ports.out.events;

import codifica.eleve.core.domain.events.solicitacao.SolicitacaoParaCadastrarResponseEvent;

public interface SolicitacaoEventPublisherPort {
    void publishSolicitacaoParaCadastrarResponse(SolicitacaoParaCadastrarResponseEvent event);
}
