package codifica.eleve.core.application.ports.in.events;

import codifica.eleve.core.domain.events.pet.PetParaCadastrarEvent;

public interface PetEventListenerPort {
    void processPetParaCadastrar(PetParaCadastrarEvent event);
}
