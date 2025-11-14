package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.usecase.pet.*;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.infrastructure.cache.PetCachingDecorators;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import({ // Importa as classes que contêm os decorators
        PetCachingDecorators.CachingListPetUseCase.class,
        PetCachingDecorators.CachingCreatePetUseCase.class,
        PetCachingDecorators.CachingUpdatePetUseCase.class,
        PetCachingDecorators.CachingDeletePetUseCase.class
})
public class PetUseCaseConfig {

    // 1. Defina os BEANS DE CACHE como @Primary
    // O Spring irá injetar estes beans nos Controllers

    @Bean
    @Primary
    public ListPetUseCase cachingListPetUseCase(PetCachingDecorators.CachingListPetUseCase useCase) {
        return useCase;
    }

    @Bean
    @Primary
    public CreatePetUseCase cachingCreatePetUseCase(PetCachingDecorators.CachingCreatePetUseCase useCase) {
        return useCase;
    }

    @Bean
    @Primary
    public UpdatePetUseCase cachingUpdatePetUseCase(PetCachingDecorators.CachingUpdatePetUseCase useCase) {
        return useCase;
    }

    @Bean
    @Primary
    public DeletePetUseCase cachingDeletePetUseCase(PetCachingDecorators.CachingDeletePetUseCase useCase) {
        return useCase;
    }

    // 2. Defina os BEANS ORIGINAIS (sem @Primary)
    // O Spring irá injetá-los automaticamente nos construtores dos decorators acima.

    @Bean
    public CreatePetUseCase createPetUseCase(PetRepository petRepository) {
        return new CreatePetUseCase(petRepository);
    }

    @Bean
    public ListPetUseCase listPetUseCase(PetRepository petRepository) {
        return new ListPetUseCase(petRepository);
    }

    @Bean
    public FindPetByIdUseCase findPetByIdUseCase(PetRepository petRepository) {
        return new FindPetByIdUseCase(petRepository);
    }

    @Bean
    public FindPetsByClienteIdUseCase findPetsByClienteIdUseCase(PetRepository petRepository) {
        return new FindPetsByClienteIdUseCase(petRepository);
    }

    @Bean
    public UpdatePetUseCase updatePetUseCase(PetRepository petRepository) {
        return new UpdatePetUseCase(petRepository);
    }

    @Bean
    public DeletePetUseCase deletePetUseCase(PetRepository petRepository, AgendaRepository agendaRepository) {
        return new DeletePetUseCase(petRepository, agendaRepository);
    }
}
