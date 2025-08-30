package codifica.eleve.infrastructure.persistence.raca.porte;

import codifica.eleve.core.domain.raca.porte.Porte;
import codifica.eleve.core.domain.raca.porte.PorteRepository;
import codifica.eleve.core.domain.shared.Id;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class PorteRepositoryImpl implements PorteRepository {

    private final PorteJpaRepository porteJpaRepository;

    public PorteRepositoryImpl(PorteJpaRepository porteJpaRepository) {
        this.porteJpaRepository = porteJpaRepository;
    }

    @Override
    public Optional<Porte> findById(Integer id) {
        return porteJpaRepository.findById(id).map(this::toDomain);
    }

    private Porte toDomain(PorteEntity entity) {
        Porte domain = new Porte();
        domain.setId(new Id(entity.getId()));
        domain.setNome(entity.getNome());
        return domain;
    }
}