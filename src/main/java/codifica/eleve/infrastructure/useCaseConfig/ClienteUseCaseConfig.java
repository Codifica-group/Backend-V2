package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.usecase.cliente.*;
import codifica.eleve.core.application.usecase.usuario.RegisterUsuarioUseCase;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.usuario.UsuarioRepository;
import codifica.eleve.infrastructure.cache.ClienteCachingDecorators;
import codifica.eleve.interfaces.dtoAdapters.ClienteDtoMapper;
import codifica.eleve.interfaces.dtoAdapters.UsuarioDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import({
        ClienteCachingDecorators.CachingListClienteUseCase.class,
        ClienteCachingDecorators.CachingCreateClienteUseCase.class,
        ClienteCachingDecorators.CachingUpdateClienteUseCase.class,
        ClienteCachingDecorators.CachingDeleteClienteUseCase.class
})
public class ClienteUseCaseConfig {

    @Bean
    @Primary
    public ListClienteUseCase cachingListClienteUseCase(ClienteCachingDecorators.CachingListClienteUseCase useCase) {
        return useCase;
    }

    @Bean
    @Primary
    public CreateClienteUseCase cachingCreateClienteUseCase(ClienteCachingDecorators.CachingCreateClienteUseCase useCase) {
        return useCase;
    }

    @Bean
    @Primary
    public UpdateClienteUseCase cachingUpdateClienteUseCase(ClienteCachingDecorators.CachingUpdateClienteUseCase useCase) {
        return useCase;
    }

    @Bean
    @Primary
    public DeleteClienteUseCase cachingDeleteClienteUseCase(ClienteCachingDecorators.CachingDeleteClienteUseCase useCase) {
        return useCase;
    }

    @Bean
    public ListClienteUseCase listClienteUseCase(ClienteRepository clienteRepository) {
        return new ListClienteUseCase(clienteRepository);
    }

    @Bean
    public CreateClienteUseCase createClienteUseCase(ClienteRepository clienteRepository) {
        return new CreateClienteUseCase(clienteRepository);
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
    public DeleteClienteUseCase deleteClienteUseCase(ClienteRepository clienteRepository, PetRepository petRepository) {
        return new DeleteClienteUseCase(clienteRepository, petRepository);
    }

    @Bean
    public CreateAppClienteUseCase createAppClienteUseCase(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository, RegisterUsuarioUseCase registerUseCase, CreateClienteUseCase createClienteUseCase, UsuarioDtoMapper usuarioMapper, ClienteDtoMapper clienteDtoMapper) {
        return new CreateAppClienteUseCase(clienteRepository, usuarioRepository, registerUseCase, createClienteUseCase, usuarioMapper, clienteDtoMapper);
    }
}
