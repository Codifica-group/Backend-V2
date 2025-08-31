package codifica.eleve.infrastructure.persistence.servico;

import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.infrastructure.adapters.ServicoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ServicoRepositoryImpl implements ServicoRepository {

    private final ServicoJpaRepository servicoJpaRepository;
    private final ServicoMapper servicoMapper;

    public ServicoRepositoryImpl(ServicoJpaRepository servicoJpaRepository, ServicoMapper servicoMapper) {
        this.servicoJpaRepository = servicoJpaRepository;
        this.servicoMapper = servicoMapper;
    }

    @Override
    public Servico save(Servico servico) {
        ServicoEntity servicoEntity = servicoMapper.toEntity(servico);
        ServicoEntity savedServico = servicoJpaRepository.save(servicoEntity);
        return servicoMapper.toDomain(savedServico);
    }

    @Override
    public Optional<Servico> findById(Integer id) {
        return servicoJpaRepository.findById(id).map(servicoMapper::toDomain);
    }

    @Override
    public List<Servico> findAll() {
        return servicoJpaRepository.findAll().stream().map(servicoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        servicoJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return servicoJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNome(String nome) {
        return servicoJpaRepository.existsByNome(nome);
    }
}
