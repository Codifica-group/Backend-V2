package codifica.eleve.core.application.ports.out.events;

import codifica.eleve.core.domain.events.cliente.ClienteParaCadastrarResponseEvent;

public interface ClienteEventPublisherPort {
    void publishClienteParaCadastrarResponse(ClienteParaCadastrarResponseEvent event);
}
