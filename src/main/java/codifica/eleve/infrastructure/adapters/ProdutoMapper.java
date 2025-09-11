package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.infrastructure.persistence.produto.ProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
    private final CategoriaProdutoMapper categoriaProdutoMapper;

    public ProdutoMapper(CategoriaProdutoMapper categoriaProdutoMapper) {
        this.categoriaProdutoMapper = categoriaProdutoMapper;
    }

    public ProdutoEntity toEntity(Produto domain) {
        ProdutoEntity entity = new ProdutoEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }
        entity.setNome(domain.getNome());
        if (domain.getCategoriaProduto() != null) {
            entity.setCategoriaProduto(categoriaProdutoMapper.toEntity(domain.getCategoriaProduto()));
        }
        return entity;
    }

    public Produto toDomain(ProdutoEntity entity) {
        CategoriaProduto categoriaProduto = categoriaProdutoMapper.toDomain(entity.getCategoriaProduto());
        Produto domain = new Produto(entity.getNome(), categoriaProduto);
        domain.setId(new Id(entity.getId()));
        return domain;
    }
}
