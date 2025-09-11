package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.infrastructure.persistence.despesa.DespesaEntity;
import codifica.eleve.infrastructure.persistence.produto.ProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class DespesaMapper {
    private final ProdutoMapper produtoMapper;

    public DespesaMapper(ProdutoMapper produtoMapper) {
        this.produtoMapper = produtoMapper;
    }

    public DespesaEntity toEntity(Despesa domain) {
        ProdutoEntity produtoEntity = produtoMapper.toEntity(domain.getProduto());
        DespesaEntity entity = new DespesaEntity(
                produtoEntity,
                domain.getValor().getValor(),
                domain.getData()
        );
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }
        return entity;
    }

    public Despesa toDomain(DespesaEntity entity) {
        Produto produto = produtoMapper.toDomain(entity.getProduto());
        ValorMonetario valor = new ValorMonetario(entity.getValor());
        Despesa domain = new Despesa(produto, valor, entity.getData());
        domain.setId(new Id(entity.getId()));
        return domain;
    }
}
