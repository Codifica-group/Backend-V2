package codifica.eleve.core.application.ports.out.events;

import codifica.eleve.core.domain.events.solicitacao.SolicitacaoParaCadastrarResponseEvent;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAtualizadaEvent;

public interface SolicitacaoEventPublisherPort {
    void publishSolicitacaoParaCadastrarResponse(SolicitacaoParaCadastrarResponseEvent event);
    void publishSolicitacaoAtualizada(SolicitacaoAtualizadaEvent event);
}
