package codifica.eleve.infrastructure.config;

import codifica.eleve.core.application.usecase.agenda.*;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgendaUseCaseConfig {

    @Bean
    public CreateAgendaUseCase createAgendaUseCase(AgendaRepository agendaRepository) {
        return new CreateAgendaUseCase(agendaRepository);
    }

    @Bean
    public FindAgendaByIdUseCase findAgendaByIdUseCase(AgendaRepository agendaRepository) {
        return new FindAgendaByIdUseCase(agendaRepository);
    }

    @Bean
    public ListAgendaUseCase listAgendaUseCase(AgendaRepository agendaRepository) {
        return new ListAgendaUseCase(agendaRepository);
    }

    @Bean
    public UpdateAgendaUseCase updateAgendaUseCase(AgendaRepository agendaRepository) {
        return new UpdateAgendaUseCase(agendaRepository);
    }

    @Bean
    public DeleteAgendaUseCase deleteAgendaUseCase(AgendaRepository agendaRepository) {
        return new DeleteAgendaUseCase(agendaRepository);
    }
}
