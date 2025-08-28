package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindUsuarioByIdUseCase {
    private final UsuarioRepository usuarioRepository;

    public FindUsuarioByIdUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario execute(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }
}
