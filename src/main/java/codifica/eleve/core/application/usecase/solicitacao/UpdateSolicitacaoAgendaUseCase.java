package codifica.eleve.core.application.usecase.solicitacao;

import codifica.eleve.core.application.ports.out.events.SolicitacaoEventPublisherPort;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.events.solicitacao.SolicitacaoAtualizadaEvent;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;
import codifica.eleve.interfaces.dto.SolicitacaoAgendaDTO;
import codifica.eleve.interfaces.dtoAdapters.SolicitacaoAgendaDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateSolicitacaoAgendaUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UpdateSolicitacaoAgendaUseCase.class);
    private final SolicitacaoAgendaRepository repository;
    private final AgendaRepository agendaRepository;
    private final SolicitacaoEventPublisherPort solicitacaoEventPublisher;
    private final SolicitacaoAgendaDtoMapper solicitacaoAgendaDtoMapper;

    public UpdateSolicitacaoAgendaUseCase(SolicitacaoAgendaRepository repository, AgendaRepository agendaRepository, SolicitacaoEventPublisherPort solicitacaoEventPublisher, SolicitacaoAgendaDtoMapper solicitacaoAgendaDtoMapper) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
        this.solicitacaoEventPublisher = solicitacaoEventPublisher;
        this.solicitacaoAgendaDtoMapper = solicitacaoAgendaDtoMapper;
    }

    public Map<String, Object> execute(Integer id, SolicitacaoAgenda solicitacaoAgenda) {
        SolicitacaoAgenda solicitacaoExistente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação de agenda não encontrada."));
        if (solicitacaoAgenda.getDataHoraInicio().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível atualizar uma solicitação de agendamento com data no passado.");
        }

        boolean aceito = "ACEITO_PELO_PETSHOP".equalsIgnoreCase(solicitacaoAgenda.getStatus());

        if (aceito && !agendaRepository.findConflitosByDataHoraInicio(solicitacaoAgenda.getDataHoraInicio()).isEmpty()) {
            throw new ConflictException("Já existe um agendamento para este período.");
        }

        boolean dataInicioAlterada = !Objects.equals(solicitacaoExistente.getDataHoraInicio(), solicitacaoAgenda.getDataHoraInicio());
        solicitacaoAgenda.setId(new Id(id));
        SolicitacaoAgenda solicitacao = repository.save(solicitacaoAgenda);
        SolicitacaoAgendaDTO solicitacaoDTO = solicitacaoAgendaDtoMapper.toDtoEvent(solicitacao);

        solicitacaoEventPublisher.publishSolicitacaoAtualizada(new SolicitacaoAtualizadaEvent(aceito, dataInicioAlterada, solicitacaoDTO));
        logger.info("EVENTO: SolicitacaoAtualizada publicado com sucesso.");

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Solicitação atualizada com sucesso.");
        response.put("id", solicitacao.getId().getValue());
        return response;
    }
}
