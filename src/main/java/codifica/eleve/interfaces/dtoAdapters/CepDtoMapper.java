package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.interfaces.dto.CepDTO;
import org.springframework.stereotype.Component;

@Component
public class CepDtoMapper {

    public CepDTO toDto(Endereco domain) {
        CepDTO dto = new CepDTO();
        dto.setCep(domain.getCep());
        dto.setLogradouro(domain.getRua());
        dto.setComplemento(domain.getComplemento());
        dto.setBairro(domain.getBairro());
        dto.setLocalidade(domain.getCidade());
        return dto;
    }
}
