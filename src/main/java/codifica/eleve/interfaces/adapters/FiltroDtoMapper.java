package codifica.eleve.interfaces.adapters;

import codifica.eleve.core.domain.agenda.Filtro;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.interfaces.dto.FiltroDTO;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class FiltroDtoMapper {

    public Filtro toDomain(FiltroDTO dto) {
        Filtro domain = new Filtro();
        if (dto.getDataInicio() != null && dto.getDataFim() != null) {
            domain.setPeriodo(new Periodo(dto.getDataInicio().atStartOfDay(), dto.getDataFim().atTime(LocalTime.MAX)));
        }
        domain.setClienteId(dto.getClienteId());
        domain.setPetId(dto.getPetId());
        domain.setRacaId(dto.getRacaId());
        domain.setServicoId(dto.getServicoId());
        return domain;
    }
}
