package codifica.eleve.core.application.ports.in.events;

import codifica.eleve.core.domain.events.cliente.ClienteParaCadastrarEvent;

public interface ClienteEventListenerPort {
    void processClienteParaCadastrar(ClienteParaCadastrarEvent event);
}
