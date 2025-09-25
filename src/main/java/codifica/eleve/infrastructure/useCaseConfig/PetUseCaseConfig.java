package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.usecase.pet.*;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.pet.PetRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetUseCaseConfig {

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
