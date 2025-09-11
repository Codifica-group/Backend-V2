package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.produto.categoria.CategoriaProdutoRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.ProdutoDTO;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDtoMapper {

    private final CategoriaProdutoRepository categoriaProdutoRepository;
    private final CategoriaProdutoDtoMapper categoriaProdutoDtoMapper;

    public ProdutoDtoMapper(CategoriaProdutoRepository categoriaProdutoRepository, CategoriaProdutoDtoMapper categoriaProdutoDtoMapper) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
        this.categoriaProdutoDtoMapper = categoriaProdutoDtoMapper;
    }

    public Produto toDomain(ProdutoDTO dto) {
        CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada."));
        return new Produto(dto.getNome(), categoriaProduto);
    }

    public ProdutoDTO toDto(Produto domain) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(domain.getId().getValue());
        dto.setNome(domain.getNome());
        dto.setCategoria(categoriaProdutoDtoMapper.toDto(domain.getCategoriaProduto()));
        return dto;
    }
}
