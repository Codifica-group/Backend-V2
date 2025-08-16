package codifica.eleve.application.usecase.usuario;

import codifica.eleve.domain.usuario.Usuario;
import codifica.eleve.domain.usuario.UsuarioRepository;

public class UpdateUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public UpdateUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String execute(Integer id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        usuario.setId(id);
        usuarioRepository.save(usuario);
        return "Usuário atualizado com sucesso.";
    }
}
