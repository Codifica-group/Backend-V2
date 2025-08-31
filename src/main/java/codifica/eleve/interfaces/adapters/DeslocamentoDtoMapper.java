package codifica.eleve.interfaces.adapters;

import codifica.eleve.core.domain.agenda.deslocamento.Deslocamento;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.interfaces.dto.DeslocamentoRequestDTO;
import codifica.eleve.interfaces.dto.DeslocamentoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class DeslocamentoDtoMapper {

    public Endereco toDomain(DeslocamentoRequestDTO dto) {
        return new Endereco(dto.getCep(), dto.getRua(), dto.getNumero(), dto.getBairro(), dto.getCidade(), null);
    }

    public DeslocamentoResponseDTO toDto(Deslocamento domain) {
        return new DeslocamentoResponseDTO(domain.getDistanciaKm(), domain.getTaxa(), domain.getTempoMinutos());
    }
}
