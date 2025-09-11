package codifica.eleve.core.domain.produto.categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaProdutoRepository {
    CategoriaProduto save(CategoriaProduto categoriaProduto);
    List<CategoriaProduto> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
    boolean existsByNome(String nome);
}
