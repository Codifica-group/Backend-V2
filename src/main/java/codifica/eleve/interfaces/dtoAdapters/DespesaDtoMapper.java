package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.produto.ProdutoRepository;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.DespesaDTO;
import codifica.eleve.interfaces.dto.ProdutoDTO;
import org.springframework.stereotype.Component;

@Component
public class DespesaDtoMapper {

    private final ProdutoRepository produtoRepository;
    private final ProdutoDtoMapper produtoDtoMapper;

    public DespesaDtoMapper(ProdutoRepository produtoRepository, ProdutoDtoMapper produtoDtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoDtoMapper = produtoDtoMapper;
    }

    public Despesa toDomain(DespesaDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        ValorMonetario valor = new ValorMonetario(dto.getValor());
        return new Despesa(produto, valor, dto.getData());
    }

    public DespesaDTO toDto(Despesa domain) {
        DespesaDTO dto = new DespesaDTO();
        dto.setId(domain.getId().getValue());
        dto.setValor(domain.getValor().getValor());
        dto.setData(domain.getData());

        ProdutoDTO produtoDTO = produtoDtoMapper.toDto(domain.getProduto());
        dto.setProduto(produtoDTO);
        return dto;
    }
}
