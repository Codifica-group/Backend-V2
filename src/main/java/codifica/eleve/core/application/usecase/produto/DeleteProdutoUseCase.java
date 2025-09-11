package codifica.eleve.core.application.usecase.produto;

import codifica.eleve.core.domain.produto.ProdutoRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteProdutoUseCase {
    private final ProdutoRepository produtoRepository;

    public DeleteProdutoUseCase(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void execute(Integer id) {
        if (!produtoRepository.existsById(id)) {
            throw new NotFoundException("Produto não encontrado.");
        }

        produtoRepository.deleteById(id);
    }
}
