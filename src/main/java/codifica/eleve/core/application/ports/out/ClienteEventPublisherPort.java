package codifica.eleve.core.application.ports.out;


import codifica.eleve.infrastructure.events.CadastroClienteResponseEvent;

public interface ClienteEventPublisherPort {
    void publicarCadastroClienteResponse(CadastroClienteResponseEvent event);
}
