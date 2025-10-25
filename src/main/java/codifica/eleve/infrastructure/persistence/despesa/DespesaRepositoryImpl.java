package codifica.eleve.infrastructure.persistence.despesa;

import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.despesa.DespesaRepository;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.infrastructure.adapters.DespesaMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DespesaRepositoryImpl implements DespesaRepository {

    private final DespesaJpaRepository despesaJpaRepository;
    private final DespesaMapper despesaMapper;

    public DespesaRepositoryImpl(DespesaJpaRepository despesaJpaRepository, DespesaMapper despesaMapper) {
        this.despesaJpaRepository = despesaJpaRepository;
        this.despesaMapper = despesaMapper;
    }

    @Override
    public Despesa save(Despesa despesa) {
        DespesaEntity entity = despesaMapper.toEntity(despesa);
        DespesaEntity saved = despesaJpaRepository.save(entity);
        return despesaMapper.toDomain(saved);
    }

    @Override
    public Optional<Despesa> findById(Integer id) {
        return despesaJpaRepository.findById(id).map(despesaMapper::toDomain);
    }

    @Override
    public List<Despesa> findAll(int offset, int size) {
        return despesaJpaRepository.findAll(PageRequest.of(offset, size)).stream().map(despesaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        despesaJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return despesaJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByProdutoId(Integer produtoId) {
        return despesaJpaRepository.existsByProdutoId(produtoId);
    }

    @Override
    public List<Despesa> findByPeriodo(Periodo periodo) {
        return despesaJpaRepository.findByDataBetween(periodo.getInicio().toLocalDate(), periodo.getFim().toLocalDate())
                .stream()
                .map(despesaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countAll() {
        return despesaJpaRepository.count();
    }
}
