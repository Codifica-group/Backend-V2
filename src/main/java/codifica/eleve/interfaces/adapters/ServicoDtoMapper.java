package codifica.eleve.interfaces.adapters;

import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.interfaces.dto.ServicoDTO;
import org.springframework.stereotype.Component;

@Component
public class ServicoDtoMapper {

    public Servico toDomain(ServicoDTO dto) {
        ValorMonetario valorMonetario = new ValorMonetario(dto.getValor());
        return new Servico(dto.getNome(), valorMonetario);
    }

    public ServicoDTO toDto(Servico domain) {
        ServicoDTO dto = new ServicoDTO();
        if (domain.getId() != null) {
            dto.setId(domain.getId().getValue());
        }
        dto.setNome(domain.getNome());
        if (domain.getValor() != null) {
            dto.setValor(domain.getValor().getValor());
        }
        return dto;
    }
}
