package codifica.eleve.infrastructure.events.listeners;

import codifica.eleve.core.application.usecase.events.PetParaCadastrarUseCase;
import codifica.eleve.core.domain.events.pet.PetParaCadastrarEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PetEventListener {

    private final PetParaCadastrarUseCase petParaCadastrarUseCase;

    public PetEventListener(PetParaCadastrarUseCase petParaCadastrarUseCase) {
        this.petParaCadastrarUseCase = petParaCadastrarUseCase;
    }

    @RabbitListener(queues = "pet.para-cadastrar.queue")
    public void onPetParaCadastrar(PetParaCadastrarEvent event) {
        petParaCadastrarUseCase.processPetParaCadastrar(event);
    }
}
