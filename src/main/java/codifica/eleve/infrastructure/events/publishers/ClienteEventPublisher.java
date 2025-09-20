package codifica.eleve.infrastructure.events.publishers;

import codifica.eleve.config.RabbitMQConfig;
import codifica.eleve.core.application.ports.out.events.ClienteEventPublisherPort;
import codifica.eleve.core.domain.events.cliente.ClienteParaCadastrarResponseEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClienteEventPublisher implements ClienteEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public ClienteEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishClienteParaCadastrarResponse(ClienteParaCadastrarResponseEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_CLIENTE_RESPONSE, event);
    }
}
