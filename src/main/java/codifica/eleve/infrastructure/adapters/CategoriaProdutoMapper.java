package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.infrastructure.persistence.produto.categoria.CategoriaProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoriaProdutoMapper {

    public CategoriaProdutoEntity toEntity(CategoriaProduto domain) {
        CategoriaProdutoEntity entity = new CategoriaProdutoEntity(domain.getNome());
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }
        return entity;
    }

    public CategoriaProduto toDomain(CategoriaProdutoEntity entity) {
        CategoriaProduto domain = new CategoriaProduto(entity.getNome());
        domain.setId(new Id(entity.getId()));
        return domain;
    }
}
