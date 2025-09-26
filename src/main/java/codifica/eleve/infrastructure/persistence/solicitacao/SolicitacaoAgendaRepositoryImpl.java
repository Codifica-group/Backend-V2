package codifica.eleve.infrastructure.persistence.solicitacao;

import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgendaRepository;
import codifica.eleve.infrastructure.adapters.SolicitacaoAgendaMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SolicitacaoAgendaRepositoryImpl implements SolicitacaoAgendaRepository {

    private final SolicitacaoAgendaJpaRepository jpaRepository;
    private final SolicitacaoAgendaMapper mapper;

    public SolicitacaoAgendaRepositoryImpl(SolicitacaoAgendaJpaRepository jpaRepository, SolicitacaoAgendaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public SolicitacaoAgenda save(SolicitacaoAgenda solicitacaoAgenda) {
        SolicitacaoAgendaEntity entity = mapper.toEntity(solicitacaoAgenda);
        SolicitacaoAgendaEntity savedEntity = jpaRepository.save(entity);
        solicitacaoAgenda.setId(new Id(savedEntity.getId()));
        return solicitacaoAgenda;
    }

    @Override
    public Optional<SolicitacaoAgenda> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<SolicitacaoAgenda> findAll(int offset, int size) {
        return jpaRepository.findAll(PageRequest.of(offset, size)).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return jpaRepository.existsById(id);
    }
}
