package codifica.eleve.interfaces.adapters;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.interfaces.dto.RacaDTO;
import org.springframework.stereotype.Component;

@Component
public class RacaDtoMapper {

    public Raca toDomain(RacaDTO dto) {
        Raca domain = new Raca();
        domain.setId(new Id(dto.getId()));
        domain.setNome(dto.getNome());
        domain.setPorte(dto.getPorte());
        return domain;
    }

    public RacaDTO toDto(Raca domain) {
        RacaDTO dto = new RacaDTO();
        dto.setId(domain.getId().getValue());
        dto.setNome(domain.getNome());
        dto.setPorte(domain.getPorte());
        return dto;
    }
}
