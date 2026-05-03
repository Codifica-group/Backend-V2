package codifica.eleve.infrastructure.persistence.raca;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaNomeUtils;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.infrastructure.adapters.RacaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RacaRepositoryImpl implements RacaRepository {

    private final RacaJpaRepository racaJpaRepository;
    private final RacaMapper racaMapper;

    public RacaRepositoryImpl(RacaJpaRepository racaJpaRepository, RacaMapper racaMapper) {
        this.racaJpaRepository = racaJpaRepository;
        this.racaMapper = racaMapper;
    }

    @Override
    public Raca save(Raca raca) {
        RacaEntity entity = racaMapper.toEntity(raca);
        RacaEntity saved = racaJpaRepository.save(entity);
        return racaMapper.toDomain(saved);
    }

    @Override
    public Optional<Raca> findById(Integer id) {
        return racaJpaRepository.findById(id).map(racaMapper::toDomain);
    }

    @Override
    public List<Raca> findAll() {
        return racaJpaRepository.findAll().stream().map(racaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        racaJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return racaJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNome(String nome) {
        String nomeCanonico = RacaNomeUtils.canonicalizar(nome);
        if (nomeCanonico.isEmpty()) {
            return false;
        }

        return racaJpaRepository.findAll().stream()
                .anyMatch(racaEntity -> RacaNomeUtils.canonicalizar(racaEntity.getNome()).equals(nomeCanonico));
    }

    @Override
    public Optional<Raca> findByNome(String nome) {
        String nomeCanonico = RacaNomeUtils.canonicalizar(nome);
        if (nomeCanonico.isEmpty()) {
            return Optional.empty();
        }

        return racaJpaRepository.findAll().stream()
                .filter(racaEntity -> RacaNomeUtils.canonicalizar(racaEntity.getNome()).equals(nomeCanonico))
                .findFirst()
                .map(racaMapper::toDomain);
    }
}
