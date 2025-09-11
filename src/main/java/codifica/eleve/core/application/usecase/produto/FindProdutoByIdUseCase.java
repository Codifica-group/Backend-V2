package codifica.eleve.core.application.usecase.produto;

import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.produto.ProdutoRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindProdutoByIdUseCase {
    private final ProdutoRepository produtoRepository;

    public FindProdutoByIdUseCase(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto execute(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
    }
}
