package codifica.eleve.core.application.usecase.events;

import codifica.eleve.core.application.ports.in.events.SolicitacaoAceitaEventListenerPort;
import codifica.eleve.core.application.ports.out.NotificationPort;
import codifica.eleve.core.application.ports.out.events.SolicitacaoAceitaResponseEventPublisherPort;
import codifica.eleve.core.application.usecase.agenda.CreateAgendaUseCase;
import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAceitaEvent;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAceitaResponseEvent;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public class ProcessoSolicitacaoAceitaUseCase implements SolicitacaoAceitaEventListenerPort {

    private static final Logger logger = LoggerFactory.getLogger(ProcessoSolicitacaoAceitaUseCase.class);

    private final SolicitacaoAgendaRepository solicitacaoAgendaRepository;
    private final CreateAgendaUseCase createAgendaUseCase;
    private final SolicitacaoAceitaResponseEventPublisherPort solicitacaoAceitaResponseEventPublisherPort;
    private final NotificationPort notificationPort;

    public ProcessoSolicitacaoAceitaUseCase(
            SolicitacaoAgendaRepository solicitacaoAgendaRepository,
            CreateAgendaUseCase createAgendaUseCase,
            SolicitacaoAceitaResponseEventPublisherPort solicitacaoAceitaResponseEventPublisherPort,
            NotificationPort notificationPort) {
        this.solicitacaoAgendaRepository = solicitacaoAgendaRepository;
        this.createAgendaUseCase = createAgendaUseCase;
        this.solicitacaoAceitaResponseEventPublisherPort = solicitacaoAceitaResponseEventPublisherPort;
        this.notificationPort = notificationPort;
    }

    @Override
    @Transactional
    public void processaSolicitacaoAceita(SolicitacaoAceitaEvent event) {
        logger.info("EVENTO: SolicitacaoAceitaEvent com solicitacaoId: {}", event.getSolicitacaoId());

        try {
            SolicitacaoAgenda solicitacao = solicitacaoAgendaRepository.findById(event.getSolicitacaoId())
                    .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
            solicitacao.setDataHoraSolicitacao(event.getDataHoraAtualizacao());

            String[] nomeCliente = solicitacao.getPet().getCliente().getNome().split(" ");

            String tituloNotificacao;
            String mensagemNotificacao;

            if (event.isAceito()) {
                solicitacao.setStatus("CONFIRMADO");

                tituloNotificacao = "Solicitação Aceita";
                mensagemNotificacao = nomeCliente[0] + " aceitou a oferta de agendamento";

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

                tituloNotificacao = "Solicitação Recusada";
                mensagemNotificacao = nomeCliente[0] + " recusou a oferta de agendamento";

                logger.info("INFO: Solicitação {} recusada pelo cliente", event.getSolicitacaoId());
            }
            Map<String, String> payload = Map.of(
                    "tipo", "SOLICITACAO_ATUALIZADA",
                    "titulo", tituloNotificacao,
                    "mensagem", mensagemNotificacao
            );
            notificationPort.notify("/topic/solicitacoes", payload);

            solicitacaoAgendaRepository.save(solicitacao);
            solicitacaoAceitaResponseEventPublisherPort.publishSolicitacaoAceitaResponse(SolicitacaoAceitaResponseEvent.sucesso(event.getChatId()));
        } catch (Exception e) {
            solicitacaoAceitaResponseEventPublisherPort.publishSolicitacaoAceitaResponse(SolicitacaoAceitaResponseEvent.falha(event.getChatId(), e.getMessage()));
            logger.error("FALHA: Erro ao processar SolicitacaoAceitaEvent: {}", e.getMessage());
        }
    }
}
