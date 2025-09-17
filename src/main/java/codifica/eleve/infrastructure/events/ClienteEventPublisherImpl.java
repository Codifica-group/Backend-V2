package codifica.eleve.infrastructure.events;

import codifica.eleve.config.RabbitMQConfig;
import codifica.eleve.core.application.ports.out.ClienteEventPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClienteEventPublisherImpl implements ClienteEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public ClienteEventPublisherImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicarCadastroClienteResponse(CadastroClienteResponseEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_CLIENTE_RESPONSE, event);
    }
}
