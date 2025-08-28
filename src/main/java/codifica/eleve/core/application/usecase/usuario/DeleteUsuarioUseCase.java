package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.domain.usuario.UsuarioRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public DeleteUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void execute(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        usuarioRepository.deleteById(id);
    }
}
