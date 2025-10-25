package codifica.eleve.infrastructure.persistence.usuario;

import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;
import codifica.eleve.infrastructure.adapters.UsuarioMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioRepositoryImpl(UsuarioJpaRepository usuarioJpaRepository, UsuarioMapper usuarioMapper) {
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = usuarioMapper.toEntity(usuario);
        UsuarioEntity saved = usuarioJpaRepository.save(entity);
        return usuarioMapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioJpaRepository.findById(id).map(entity -> usuarioMapper.toDomain(entity));
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        UsuarioEntity entity = usuarioJpaRepository.findByEmail(email);
        return Optional.ofNullable(entity).map(userEntity -> usuarioMapper.toDomain(userEntity));
    }

    @Override
    public Optional<Usuario> findByEmailAndSenha(String email, String senha) {
        UsuarioEntity entity = usuarioJpaRepository.findByEmailAndSenha(email, senha);
        return Optional.ofNullable(entity).map(userEntity -> usuarioMapper.toDomain(userEntity));
    }

    @Override
    public List<Usuario> findAll(int offset, int size) {
        return usuarioJpaRepository.findAll(PageRequest.of(offset, size)).stream().map(entity -> usuarioMapper.toDomain(entity)).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Integer id) {
        return usuarioJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        usuarioJpaRepository.deleteById(id);
    }

    @Override
    public long countAll() {
        return usuarioJpaRepository.count();
    }
}
