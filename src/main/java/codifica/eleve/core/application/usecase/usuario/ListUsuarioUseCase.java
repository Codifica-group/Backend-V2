package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;

import java.util.List;

public class ListUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public ListUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> execute() {
        return usuarioRepository.findAll();
    }
}
