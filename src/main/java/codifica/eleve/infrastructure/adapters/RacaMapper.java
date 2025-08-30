package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.infrastructure.persistence.raca.RacaEntity;
import org.springframework.stereotype.Component;

@Component
public class RacaMapper {

    public RacaEntity toEntity(Raca domain) {
        if (domain == null) {
            return null;
        }
        RacaEntity entity = new RacaEntity();
        entity.setId(domain.getId().getValue());
        entity.setNome(domain.getNome());
        entity.setPorte(domain.getPorte());
        return entity;
    }

    public Raca toDomain(RacaEntity entity) {
        if (entity == null) {
            return null;
        }
        Raca domain = new Raca();
        domain.setId(new Id(entity.getId()));
        domain.setNome(entity.getNome());
        domain.setPorte(entity.getPorte());
        return domain;
    }
}
