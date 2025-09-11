package codifica.eleve.core.application.usecase.produto;

import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.produto.ProdutoRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import java.util.HashMap;
import java.util.Map;

public class CreateProdutoUseCase {
    private final ProdutoRepository produtoRepository;

    public CreateProdutoUseCase(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Map<String, Object> execute(Produto produto) {
        if (produtoRepository.existsByCategoriaProdutoAndNome(produto.getCategoriaProduto().getId().getValue(), produto.getNome())) {
            throw new ConflictException("Não é possível cadastrar dois produtos iguais na mesma categoria.");
        }

        Produto produtoSalvo = produtoRepository.save(produto);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Produto cadastrado com sucesso.");
        response.put("id", produtoSalvo.getId().getValue());
        return response;
    }
}
