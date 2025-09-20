package codifica.eleve.infrastructure.events.publishers;

import codifica.eleve.config.RabbitMQConfig;
import codifica.eleve.core.application.ports.out.events.PetEventPublisherPort;
import codifica.eleve.core.domain.events.pet.PetParaCadastrarResponseEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PetEventPublisher implements PetEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public PetEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishPetParaCadastrarResponse(PetParaCadastrarResponseEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_PET_RESPONSE, event);
    }
}
