package codifica.eleve.infrastructure.events.publishers;

import codifica.eleve.core.application.ports.out.events.SolicitacaoAceitaResponseEventPublisherPort;
import codifica.eleve.core.application.ports.out.events.SolicitacaoEventPublisherPort;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAceitaResponseEvent;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAtualizadaEvent;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoParaCadastrarResponseEvent;
import codifica.eleve.infrastructure.rabbitMQ.RabbitMQConfig;
import codifica.eleve.infrastructure.rabbitMQ.SolicitacaoRabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SolicitacaoEventPublisher implements SolicitacaoEventPublisherPort, SolicitacaoAceitaResponseEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public SolicitacaoEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishSolicitacaoParaCadastrarResponse(SolicitacaoParaCadastrarResponseEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                SolicitacaoRabbitMQConfig.ROUTING_KEY_SOLICITACAO_RESPONSE,
                event
        );
    }

    @Override
    public void publishSolicitacaoAtualizada(SolicitacaoAtualizadaEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                SolicitacaoRabbitMQConfig.ROUTING_KEY_SOLICITACAO_ATUALIZADA,
                event
        );
    }

    @Override
    public void publishSolicitacaoAceitaResponse(SolicitacaoAceitaResponseEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                SolicitacaoRabbitMQConfig.ROUTING_KEY_SOLICITACAO_ACEITA_RESPONSE,
                event
        );
    }
}
