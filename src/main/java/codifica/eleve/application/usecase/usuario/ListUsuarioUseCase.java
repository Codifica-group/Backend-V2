package codifica.eleve.application.usecase.usuario;

import codifica.eleve.domain.usuario.Usuario;
import codifica.eleve.domain.usuario.UsuarioRepository;

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
