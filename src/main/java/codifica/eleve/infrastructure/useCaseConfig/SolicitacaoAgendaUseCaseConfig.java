package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.usecase.solicitacao.*;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolicitacaoAgendaUseCaseConfig {

    @Bean
    public CreateSolicitacaoAgendaUseCase createSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository, AgendaRepository agendaRepository) {
        return new CreateSolicitacaoAgendaUseCase(repository, agendaRepository);
    }

    @Bean
    public FindSolicitacaoAgendaByIdUseCase findSolicitacaoAgendaByIdUseCase(SolicitacaoAgendaRepository repository) {
        return new FindSolicitacaoAgendaByIdUseCase(repository);
    }

    @Bean
    public ListSolicitacaoAgendaUseCase listSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository) {
        return new ListSolicitacaoAgendaUseCase(repository);
    }

    @Bean
    public UpdateSolicitacaoAgendaUseCase updateSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository, AgendaRepository agendaRepository) {
        return new UpdateSolicitacaoAgendaUseCase(repository, agendaRepository);
    }

    @Bean
    public DeleteSolicitacaoAgendaUseCase deleteSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository) {
        return new DeleteSolicitacaoAgendaUseCase(repository);
    }
}
