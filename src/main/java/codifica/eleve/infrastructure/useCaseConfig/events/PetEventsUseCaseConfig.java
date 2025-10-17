package codifica.eleve.infrastructure.useCaseConfig.events;

import codifica.eleve.core.application.ports.out.events.PetEventPublisherPort;
import codifica.eleve.core.application.usecase.events.PetParaCadastrarUseCase;
import codifica.eleve.core.application.usecase.pet.CreatePetUseCase;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.infrastructure.events.listeners.PetEventListener;
import codifica.eleve.interfaces.dtoAdapters.RacaDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetEventsUseCaseConfig {

    @Bean
    public PetParaCadastrarUseCase petParaCadastrarUseCase(
            ClienteRepository clienteRepository,
            CreatePetUseCase createPetUseCase,
            PetEventPublisherPort petEventPublisherPort,
            RacaDtoMapper racaDtoMapper) {
        return new PetParaCadastrarUseCase(clienteRepository, createPetUseCase, petEventPublisherPort, racaDtoMapper);
    }

    @Bean
    public PetEventListener petEventListener(PetParaCadastrarUseCase petParaCadastrarUseCase) {
        return new PetEventListener(petParaCadastrarUseCase);
    }
}
