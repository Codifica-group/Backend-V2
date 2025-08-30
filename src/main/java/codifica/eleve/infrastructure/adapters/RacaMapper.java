package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.porte.Porte;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.infrastructure.persistence.raca.RacaEntity;
import codifica.eleve.infrastructure.persistence.raca.porte.PorteEntity;
import org.springframework.stereotype.Component;

@Component
public class RacaMapper {

    public RacaEntity toEntity(Raca domain) {
        RacaEntity entity = new RacaEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }
        entity.setNome(domain.getNome());

        PorteEntity porteEntity = new PorteEntity();
        porteEntity.setId(domain.getPorte().getId().getValue());
        porteEntity.setNome(domain.getPorte().getNome());
        entity.setPorte(porteEntity);
        return entity;
    }

    public Raca toDomain(RacaEntity entity) {
        Porte porteDomain = new Porte();
        porteDomain.setId(new Id(entity.getPorte().getId()));
        porteDomain.setNome(entity.getPorte().getNome());

        Raca domain = new Raca(entity.getNome(), porteDomain);
        domain.setId(new Id(entity.getId()));
        return domain;
    }
}
