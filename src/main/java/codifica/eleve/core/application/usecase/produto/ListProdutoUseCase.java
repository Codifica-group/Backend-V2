package codifica.eleve.core.application.usecase.produto;

import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.produto.ProdutoRepository;
import java.util.List;

public class ListProdutoUseCase {
    private final ProdutoRepository produtoRepository;

    public ListProdutoUseCase(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> execute() {
        return produtoRepository.findAll();
    }
}
