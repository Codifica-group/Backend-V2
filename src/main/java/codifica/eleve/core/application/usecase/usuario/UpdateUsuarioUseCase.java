package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class UpdateUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public UpdateUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String execute(Integer id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        usuario.setId(new Id(id));
        usuarioRepository.save(usuario);
        return "Usuário atualizado com sucesso.";
    }
}
