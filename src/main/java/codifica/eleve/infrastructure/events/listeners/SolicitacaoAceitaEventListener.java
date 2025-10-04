package codifica.eleve.infrastructure.events.listeners;

import codifica.eleve.core.application.ports.in.events.SolicitacaoAceitaEventListenerPort;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAceitaEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SolicitacaoAceitaEventListener {

    private final SolicitacaoAceitaEventListenerPort solicitacaoAceitaEventListenerPort;

    public SolicitacaoAceitaEventListener(SolicitacaoAceitaEventListenerPort solicitacaoAceitaEventListenerPort) {
        this.solicitacaoAceitaEventListenerPort = solicitacaoAceitaEventListenerPort;
    }

    @RabbitListener(queues = "solicitacao.aceita.queue")
    public void onSolicitacaoAceita(SolicitacaoAceitaEvent event) {
        solicitacaoAceitaEventListenerPort.processaSolicitacaoAceita(event);
    }
}
