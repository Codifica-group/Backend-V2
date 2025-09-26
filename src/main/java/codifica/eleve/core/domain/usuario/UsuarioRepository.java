package codifica.eleve.core.domain.usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Integer id);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
    List<Usuario> findAll(int offset, int size);
    boolean existsById(Integer id);
    void deleteById(Integer id);
}
