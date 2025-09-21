package codifica.eleve.core.application.ports.in.events;

import codifica.eleve.core.domain.events.solicitacao.SolicitacaoParaCadastrarEvent;

public interface SolicitacaoEventListenerPort {
    void processSolicitacaoParaCadastrar(SolicitacaoParaCadastrarEvent event);
}
