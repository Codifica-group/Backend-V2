package codifica.eleve.core.application.usecase.events;

import codifica.eleve.core.application.ports.in.events.SolicitacaoEventListenerPort;
import codifica.eleve.core.application.ports.out.events.SolicitacaoEventPublisherPort;
import codifica.eleve.core.application.usecase.solicitacao.CreateSolicitacaoAgendaUseCase;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoParaCadastrarEvent;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoParaCadastrarResponseEvent;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SolicitacaoParaCadastrarUseCase implements SolicitacaoEventListenerPort {

    private static final Logger logger = LoggerFactory.getLogger(SolicitacaoParaCadastrarUseCase.class);

    private final CreateSolicitacaoAgendaUseCase createSolicitacaoAgendaUseCase;
    private final PetRepository petRepository;
    private final ServicoRepository servicoRepository;
    private final SolicitacaoEventPublisherPort solicitacaoEventPublisher;

    public SolicitacaoParaCadastrarUseCase(
            CreateSolicitacaoAgendaUseCase createSolicitacaoAgendaUseCase,
            PetRepository petRepository,
            ServicoRepository servicoRepository,
            SolicitacaoEventPublisherPort solicitacaoEventPublisher) {
        this.createSolicitacaoAgendaUseCase = createSolicitacaoAgendaUseCase;
        this.petRepository = petRepository;
        this.servicoRepository = servicoRepository;
        this.solicitacaoEventPublisher = solicitacaoEventPublisher;
    }

    @Override
    public void processSolicitacaoParaCadastrar(SolicitacaoParaCadastrarEvent event) {
        logger.info("EVENTO: Solicitacao Para Cadastrar com chatId: {}", event.getChatId());
        try {
            Pet pet = petRepository.findById(event.getPetId())
                    .orElseThrow(() -> new NotFoundException("Pet não encontrado."));

            List<Servico> servicos = event.getServicos().stream()
                    .map(servicoId -> servicoRepository.findById(servicoId)
                            .orElseThrow(() -> new NotFoundException("Serviço não encontrado.")))
                    .collect(Collectors.toList());

            SolicitacaoAgenda solicitacaoAgenda = new SolicitacaoAgenda(
                    pet,
                    servicos,
                    new ValorMonetario(BigDecimal.ZERO),
                    event.getDataHoraInicio(),
                    null,
                    event.getDataHoraSolicitacao(),
                    event.getStatus()
            );

            Map<String, Object> response = createSolicitacaoAgendaUseCase.execute(solicitacaoAgenda);
            Integer solicitacaoId = (Integer) response.get("id");
            logger.info("SUCESSO: Solicitacao do chatId {} cadastrada com Id: {}", event.getChatId(), solicitacaoId);

            solicitacaoEventPublisher.publishSolicitacaoParaCadastrarResponse(
                    SolicitacaoParaCadastrarResponseEvent.sucesso(event.getChatId(), solicitacaoId)
            );

        } catch (Exception e) {
            logger.error("FALHA: Erro ao cadastrar solicitacao do chatId {}: {}", event.getChatId(), e.getMessage());

            solicitacaoEventPublisher.publishSolicitacaoParaCadastrarResponse(
                    SolicitacaoParaCadastrarResponseEvent.falha(event.getChatId(), e.getMessage())
            );
        }
    }
}
