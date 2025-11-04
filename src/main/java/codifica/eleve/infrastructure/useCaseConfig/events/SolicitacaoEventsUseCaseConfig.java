package codifica.eleve.infrastructure.useCaseConfig.events;

import codifica.eleve.core.application.ports.out.NotificationPort;
import codifica.eleve.core.application.ports.out.events.SolicitacaoAceitaResponseEventPublisherPort;
import codifica.eleve.core.application.ports.out.events.SolicitacaoEventPublisherPort;
import codifica.eleve.core.application.usecase.agenda.CreateAgendaUseCase;
import codifica.eleve.core.application.usecase.events.ProcessoSolicitacaoAceitaUseCase;
import codifica.eleve.core.application.usecase.events.SolicitacaoParaCadastrarUseCase;
import codifica.eleve.core.application.usecase.solicitacao.CreateSolicitacaoAgendaUseCase;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;
import codifica.eleve.infrastructure.events.listeners.SolicitacaoAceitaEventListener;
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
            SolicitacaoEventPublisherPort solicitacaoEventPublisher,
            NotificationPort notificationPort) {
        return new SolicitacaoParaCadastrarUseCase(createSolicitacaoAgendaUseCase, petRepository, servicoRepository, solicitacaoEventPublisher, notificationPort);
    }

    @Bean
    public ProcessoSolicitacaoAceitaUseCase processoSolicitacaoAceitaUseCase(
            SolicitacaoAgendaRepository solicitacaoAgendaRepository,
            CreateAgendaUseCase createAgendaUseCase,
            SolicitacaoAceitaResponseEventPublisherPort solicitacaoAceitaResponseEventPublisherPort,
            NotificationPort notificationPort) {
        return new ProcessoSolicitacaoAceitaUseCase(solicitacaoAgendaRepository, createAgendaUseCase, solicitacaoAceitaResponseEventPublisherPort, notificationPort);
    }

    @Bean
    public SolicitacaoEventListener solicitacaoEventListener(SolicitacaoParaCadastrarUseCase solicitacaoParaCadastrarUseCase) {
        return new SolicitacaoEventListener(solicitacaoParaCadastrarUseCase);
    }

    @Bean
    public SolicitacaoAceitaEventListener solicitacaoAceitaEventListener(ProcessoSolicitacaoAceitaUseCase processoSolicitacaoAceitaUseCase) {
        return new SolicitacaoAceitaEventListener(processoSolicitacaoAceitaUseCase);
    }
}
