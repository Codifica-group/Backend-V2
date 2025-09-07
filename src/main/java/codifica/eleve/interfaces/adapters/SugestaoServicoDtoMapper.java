package codifica.eleve.interfaces.adapters;

import codifica.eleve.core.domain.agenda.calculadora.SugestaoServico;
import codifica.eleve.interfaces.dto.DeslocamentoResponseDTO;
import codifica.eleve.interfaces.dto.ServicoDTO;
import codifica.eleve.interfaces.dto.SugestaoServicoDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

@Component
public class SugestaoServicoDtoMapper {

    public SugestaoServicoDTO toDto(SugestaoServico domain) {
        SugestaoServicoDTO dto = new SugestaoServicoDTO();
        dto.setValor(domain.getValor().setScale(2, RoundingMode.HALF_UP));

        dto.setServicos(domain.getServicos().stream().map(servico -> {
            ServicoDTO servicoDto = new ServicoDTO();
            servicoDto.setId(servico.getId().getValue());
            servicoDto.setNome(servico.getNome());
            servicoDto.setValor(servico.getValor().getValor().setScale(2, RoundingMode.HALF_UP));
            return servicoDto;
        }).collect(Collectors.toList()));

        DeslocamentoResponseDTO deslocamentoDto = new DeslocamentoResponseDTO(
                BigDecimal.valueOf(domain.getDeslocamento().getDistanciaKm()).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(domain.getDeslocamento().getTaxa()).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(domain.getDeslocamento().getTempoMinutos()).setScale(2, RoundingMode.HALF_UP)
        );
        dto.setDeslocamento(deslocamentoDto);

        return dto;
    }
}
