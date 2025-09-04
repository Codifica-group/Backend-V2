package codifica.eleve.infrastructure.config;

import codifica.eleve.core.application.usecase.agenda.CreateAgendaUseCase;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgendaUseCaseConfig {

    @Bean
    public CreateAgendaUseCase createAgendaUseCase(AgendaRepository agendaRepository) {
        return new CreateAgendaUseCase(agendaRepository);
    }
}
