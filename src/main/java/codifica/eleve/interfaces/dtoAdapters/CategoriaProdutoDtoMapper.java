package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.interfaces.dto.CategoriaProdutoDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoriaProdutoDtoMapper {

    public CategoriaProduto toDomain(CategoriaProdutoDTO dto) {
        return new CategoriaProduto(dto.getNome());
    }

    public CategoriaProdutoDTO toDto(CategoriaProduto domain) {
        CategoriaProdutoDTO dto = new CategoriaProdutoDTO();
        if (domain.getId() != null) {
            dto.setId(domain.getId().getValue());
        }
        dto.setNome(domain.getNome());
        return dto;
    }
}
