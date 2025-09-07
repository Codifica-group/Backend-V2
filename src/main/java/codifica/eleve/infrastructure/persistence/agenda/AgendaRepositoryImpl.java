package codifica.eleve.infrastructure.persistence.agenda;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.agenda.Filtro;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.infrastructure.adapters.AgendaMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AgendaRepositoryImpl implements AgendaRepository {

    private final AgendaJpaRepository agendaJpaRepository;
    private final AgendaMapper agendaMapper;

    public AgendaRepositoryImpl(AgendaJpaRepository agendaJpaRepository, AgendaMapper agendaMapper) {
        this.agendaJpaRepository = agendaJpaRepository;
        this.agendaMapper = agendaMapper;
    }

    @Override
    public Agenda save(Agenda agenda) {
        AgendaEntity entity = agendaMapper.toEntity(agenda);
        AgendaEntity saved = agendaJpaRepository.save(entity);
        agenda.setId(new Id(saved.getId()));
        return agenda;
    }

    @Override
    public Optional<Agenda> findById(Integer id) {
        return agendaJpaRepository.findById(id).map(agendaMapper::toDomain);
    }

    @Override
    public List<Agenda> findAll() {
        return agendaJpaRepository.findAll()
                .stream()
                .map(agendaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Agenda> findConflitos(Periodo periodo) {
        return agendaJpaRepository.findConflitos(periodo.getInicio(), periodo.getFim())
                .stream()
                .map(agendaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Agenda> findConflitosExcluindoId(Integer id, Periodo periodo) {
        return agendaJpaRepository.findConflitosExcluindoId(id, periodo.getInicio(), periodo.getFim())
                .stream()
                .map(agendaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        agendaJpaRepository.deleteById(id);
    }

    @Override
    public List<Agenda> findByFilter(Filtro filtro) {
        LocalDateTime dataInicio = null;
        LocalDateTime dataFim = null;
        if (filtro.getPeriodo() != null) {
            dataInicio = filtro.getPeriodo().getInicio();
            dataFim = filtro.getPeriodo().getFim();
        }

        List<Integer> servicoId = filtro.getServicoId();
        Long servicoIdSize = servicoId != null ? (long) servicoId.size() : null;
        if (servicoId != null && servicoId.isEmpty()) {
            servicoId = null;
            servicoIdSize = null;
        }

        return agendaJpaRepository.findByFilter(
                        dataInicio,
                        dataFim,
                        filtro.getClienteId(),
                        filtro.getPetId(),
                        filtro.getRacaId(),
                        servicoId,
                        servicoIdSize)
                .stream()
                .map(agendaMapper::toDomain)
                .collect(Collectors.toList());
    }
}
