package codifica.eleve.core.application.ports.in.events;

import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAceitaEvent;

public interface SolicitacaoAceitaEventListenerPort {
    void processaSolicitacaoAceita(SolicitacaoAceitaEvent event);
}
