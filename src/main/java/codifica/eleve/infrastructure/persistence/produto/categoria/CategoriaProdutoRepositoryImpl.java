package codifica.eleve.infrastructure.persistence.produto.categoria;

import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.produto.categoria.CategoriaProdutoRepository;
import codifica.eleve.infrastructure.adapters.CategoriaProdutoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CategoriaProdutoRepositoryImpl implements CategoriaProdutoRepository {

    private final CategoriaProdutoJpaRepository categoriaProdutoJpaRepository;
    private final CategoriaProdutoMapper categoriaProdutoMapper;

    public CategoriaProdutoRepositoryImpl(CategoriaProdutoJpaRepository categoriaProdutoJpaRepository, CategoriaProdutoMapper categoriaProdutoMapper) {
        this.categoriaProdutoJpaRepository = categoriaProdutoJpaRepository;
        this.categoriaProdutoMapper = categoriaProdutoMapper;
    }

    @Override
    public CategoriaProduto save(CategoriaProduto categoriaProduto) {
        CategoriaProdutoEntity entity = categoriaProdutoMapper.toEntity(categoriaProduto);
        CategoriaProdutoEntity saved = categoriaProdutoJpaRepository.save(entity);
        return categoriaProdutoMapper.toDomain(saved);
    }

    @Override
    public List<CategoriaProduto> findAll() {
        return categoriaProdutoJpaRepository.findAll().stream().map(categoriaProdutoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        categoriaProdutoJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return categoriaProdutoJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNome(String nome) {
        return categoriaProdutoJpaRepository.existsByNome(nome);
    }
}
