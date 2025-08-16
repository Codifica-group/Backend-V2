package codifica.eleve.application.usecase.usuario;

import codifica.eleve.domain.usuario.Usuario;
import codifica.eleve.domain.usuario.UsuarioRepository;

public class FindUsuarioByIdUseCase {
    private final UsuarioRepository usuarioRepository;

    public FindUsuarioByIdUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario execute(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }
}
