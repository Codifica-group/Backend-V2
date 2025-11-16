package codifica.eleve.infrastructure.useCaseConfig.events;

import codifica.eleve.core.application.ports.out.events.ClienteEventPublisherPort;
import codifica.eleve.core.application.usecase.cliente.CreateClienteUseCase;
import codifica.eleve.core.application.usecase.events.ClienteParaCadastrarUseCase;
import codifica.eleve.infrastructure.events.listeners.ClienteEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteEventsUseCaseConfig {

    @Bean
    public ClienteParaCadastrarUseCase clienteParaCadastrarUseCase(
            CreateClienteUseCase createClienteUseCase,
            ClienteEventPublisherPort clienteEventPublisherPort) {
        return new ClienteParaCadastrarUseCase(createClienteUseCase, clienteEventPublisherPort);
    }

    @Bean
    public ClienteEventListener clienteEventListener(ClienteParaCadastrarUseCase clienteParaCadastrarUseCase) {
        return new ClienteEventListener(clienteParaCadastrarUseCase);
    }
}
