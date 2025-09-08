package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.porte.Porte;
import codifica.eleve.core.domain.raca.porte.PorteRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.RacaDTO;
import org.springframework.stereotype.Component;

@Component
public class RacaDtoMapper {

    private final PorteRepository porteRepository;

    public RacaDtoMapper(PorteRepository porteRepository) {
        this.porteRepository = porteRepository;
    }

    public Raca toDomain(RacaDTO dto) {
        Porte porte = porteRepository.findById(dto.getPorteId())
                .orElseThrow(() -> new NotFoundException("Porte não encontrado."));

        Raca domain = new Raca(dto.getNome(), porte);
        return domain;
    }

    public RacaDTO toDto(Raca domain) {
        RacaDTO dto = new RacaDTO();
        dto.setId(domain.getId().getValue());
        dto.setNome(domain.getNome());
        dto.setPorteId(domain.getPorte().getId().getValue());
        return dto;
    }
}
