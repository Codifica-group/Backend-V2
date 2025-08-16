package codifica.eleve.application.usecase.usuario;

import codifica.eleve.domain.usuario.UsuarioRepository;

public class DeleteUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public DeleteUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void execute(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        usuarioRepository.deleteById(id);
    }
}
