package codifica.eleve.core.application.ports.out;

import codifica.eleve.infrastructure.events.dto.ClienteCadastradoEvent;
import codifica.eleve.infrastructure.events.dto.FalhaCadastroClienteEvent;

public interface ClienteEventPublisherPort {
    void publicarClienteCadastrado(ClienteCadastradoEvent event);
    void publicarFalhaCadastro(FalhaCadastroClienteEvent event);
}
