package codifica.eleve.infrastructure.useCaseConfig.events;

import codifica.eleve.core.application.ports.out.events.SolicitacaoEventPublisherPort;
import codifica.eleve.core.application.usecase.events.SolicitacaoParaCadastrarUseCase;
import codifica.eleve.core.application.usecase.solicitacao.CreateSolicitacaoAgendaUseCase;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.infrastructure.events.listeners.SolicitacaoEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolicitacaoEventsUseCaseConfig {

    @Bean
    public SolicitacaoParaCadastrarUseCase solicitacaoParaCadastrarUseCase(
            CreateSolicitacaoAgendaUseCase createSolicitacaoAgendaUseCase,
            PetRepository petRepository,
            ServicoRepository servicoRepository,
            SolicitacaoEventPublisherPort solicitacaoEventPublisher) {
        return new SolicitacaoParaCadastrarUseCase(createSolicitacaoAgendaUseCase, petRepository, servicoRepository, solicitacaoEventPublisher);
    }

    @Bean
    public SolicitacaoEventListener solicitacaoEventListener(SolicitacaoParaCadastrarUseCase solicitacaoParaCadastrarUseCase) {
        return new SolicitacaoEventListener(solicitacaoParaCadastrarUseCase);
    }
}
