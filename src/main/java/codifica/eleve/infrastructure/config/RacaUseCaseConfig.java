package codifica.eleve.infrastructure.config;

import codifica.eleve.core.application.usecase.raca.*;
import codifica.eleve.core.domain.raca.RacaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RacaUseCaseConfig {

    @Bean
    public CreateRacaUseCase createRacaUseCase(RacaRepository racaRepository) {
        return new CreateRacaUseCase(racaRepository);
    }

    @Bean
    public ListRacaUseCase listRacaUseCase(RacaRepository racaRepository) {
        return new ListRacaUseCase(racaRepository);
    }

    @Bean
    public FindRacaByIdUseCase findRacaByIdUseCase(RacaRepository racaRepository) {
        return new FindRacaByIdUseCase(racaRepository);
    }

    @Bean
    public UpdateRacaUseCase updateRacaUseCase(RacaRepository racaRepository) {
        return new UpdateRacaUseCase(racaRepository);
    }

    @Bean
    public DeleteRacaUseCase deleteRacaUseCase(RacaRepository racaRepository) {
        return new DeleteRacaUseCase(racaRepository);
    }
}