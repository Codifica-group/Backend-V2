package codifica.eleve.core.application.ports.out.events;

import codifica.eleve.core.domain.events.pet.PetParaCadastrarResponseEvent;

public interface PetEventPublisherPort {
    void publishPetParaCadastrarResponse(PetParaCadastrarResponseEvent event);
}
