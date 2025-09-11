package codifica.eleve.core.application.usecase.produto;

import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.produto.ProdutoRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class UpdateProdutoUseCase {
    private final ProdutoRepository produtoRepository;

    public UpdateProdutoUseCase(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public String execute(Integer id, Produto produto) {
        if (!produtoRepository.existsById(id)) {
            throw new NotFoundException("Produto não encontrado.");
        }
        produto.setId(new Id(id));
        produtoRepository.save(produto);
        return "Produto atualizado com sucesso.";
    }
}
