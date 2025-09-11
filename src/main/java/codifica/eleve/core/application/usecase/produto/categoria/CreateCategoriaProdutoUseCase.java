package codifica.eleve.core.application.usecase.produto.categoria;

import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.produto.categoria.CategoriaProdutoRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import java.util.HashMap;
import java.util.Map;

public class CreateCategoriaProdutoUseCase {
    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public CreateCategoriaProdutoUseCase(CategoriaProdutoRepository categoriaProdutoRepository) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    public Map<String, Object> execute(CategoriaProduto categoriaProduto) {
        if (categoriaProdutoRepository.existsByNome(categoriaProduto.getNome())) {
            throw new ConflictException("Impossível cadastrar duas categorias com o mesmo nome.");
        }

        CategoriaProduto novaCategoria = categoriaProdutoRepository.save(categoriaProduto);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Categoria cadastrada com sucesso.");
        response.put("id", novaCategoria.getId().getValue());
        return response;
    }
}
