package codifica.eleve.core.application.usecase.events;

import codifica.eleve.core.application.ports.in.events.SolicitacaoAceitaEventListenerPort;
import codifica.eleve.core.application.ports.out.events.SolicitacaoAceitaResponseEventPublisherPort;
import codifica.eleve.core.application.usecase.agenda.CreateAgendaUseCase;
import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAceitaEvent;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAceitaResponseEvent;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class ProcessoSolicitacaoAceitaUseCase implements SolicitacaoAceitaEventListenerPort {

    private static final Logger logger = LoggerFactory.getLogger(ProcessoSolicitacaoAceitaUseCase.class);

    private final SolicitacaoAgendaRepository solicitacaoAgendaRepository;
    private final CreateAgendaUseCase createAgendaUseCase;
    private final SolicitacaoAceitaResponseEventPublisherPort solicitacaoAceitaResponseEventPublisherPort;

    public ProcessoSolicitacaoAceitaUseCase(
            SolicitacaoAgendaRepository solicitacaoAgendaRepository,
            CreateAgendaUseCase createAgendaUseCase,
            SolicitacaoAceitaResponseEventPublisherPort solicitacaoAceitaResponseEventPublisherPort) {
        this.solicitacaoAgendaRepository = solicitacaoAgendaRepository;
        this.createAgendaUseCase = createAgendaUseCase;
        this.solicitacaoAceitaResponseEventPublisherPort = solicitacaoAceitaResponseEventPublisherPort;
    }

    @Override
    @Transactional
    public void processaSolicitacaoAceita(SolicitacaoAceitaEvent event) {
        logger.info("EVENTO: SolicitacaoAceitaEvent com solicitacaoId: {}", event.getSolicitacaoId());

        try {
            SolicitacaoAgenda solicitacao = solicitacaoAgendaRepository.findById(event.getSolicitacaoId())
                    .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
            solicitacao.setDataHoraSolicitacao(event.getDataHoraAtualizacao());

            if (event.isAceito()) {
                solicitacao.setStatus("CONFIRMADO");

                Agenda agenda = new Agenda(
                        solicitacao.getPet(),
                        solicitacao.getServicos(),
                        solicitacao.getValorDeslocamento(),
                        new Periodo(solicitacao.getDataHoraInicio(), solicitacao.getDataHoraFim())
                );
                createAgendaUseCase.execute(agenda);
                logger.info("SUCESSO: Agenda criada a partir da solicitação {}", event.getSolicitacaoId());
            } else {
                solicitacao.setStatus("RECUSADO_PELO_CLIENTE");
                logger.info("INFO: Solicitação {} recusada pelo cliente", event.getSolicitacaoId());
            }

            solicitacaoAgendaRepository.save(solicitacao);
            solicitacaoAceitaResponseEventPublisherPort.publishSolicitacaoAceitaResponse(SolicitacaoAceitaResponseEvent.sucesso(event.getChatId()));
        } catch (Exception e) {
            solicitacaoAceitaResponseEventPublisherPort.publishSolicitacaoAceitaResponse(SolicitacaoAceitaResponseEvent.falha(event.getChatId(), e.getMessage()));
            logger.error("FALHA: Erro ao processar SolicitacaoAceitaEvent: {}", e.getMessage());
        }
    }
}
