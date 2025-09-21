package codifica.eleve.infrastructure.events.listeners;

import codifica.eleve.core.application.usecase.events.SolicitacaoParaCadastrarUseCase;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoParaCadastrarEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SolicitacaoEventListener {

    private final SolicitacaoParaCadastrarUseCase solicitacaoParaCadastrarUseCase;

    public SolicitacaoEventListener(SolicitacaoParaCadastrarUseCase solicitacaoParaCadastrarUseCase) {
        this.solicitacaoParaCadastrarUseCase = solicitacaoParaCadastrarUseCase;
    }

    @RabbitListener(queues = "solicitacao.para-cadastrar.queue")
    public void onSolicitacaoParaCadastrar(SolicitacaoParaCadastrarEvent event) {
        solicitacaoParaCadastrarUseCase.processSolicitacaoParaCadastrar(event);
    }
}
