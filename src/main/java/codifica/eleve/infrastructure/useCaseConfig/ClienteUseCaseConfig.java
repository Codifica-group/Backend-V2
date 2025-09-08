package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.usecase.cliente.*;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteUseCaseConfig {
    @Bean
    public CreateClienteUseCase createClienteUseCase(ClienteRepository clienteRepository) {
        return new CreateClienteUseCase(clienteRepository);
    }

    @Bean
    public ListClienteUseCase listClienteUseCase(ClienteRepository clienteRepository) {
        return new ListClienteUseCase(clienteRepository);
    }

    @Bean
    public FindClienteByIdUseCase findClienteByIdUseCase(ClienteRepository clienteRepository) {
        return new FindClienteByIdUseCase(clienteRepository);
    }

    @Bean
    public UpdateClienteUseCase updateClienteUseCase(ClienteRepository clienteRepository) {
        return new UpdateClienteUseCase(clienteRepository);
    }

    @Bean
    public DeleteClienteUseCase deleteClienteUseCase(ClienteRepository clienteRepository) {
        return new DeleteClienteUseCase(clienteRepository);
    }
}
