package codifica.eleve.application.usecase.usuario;

import codifica.eleve.domain.usuario.Usuario;
import codifica.eleve.domain.usuario.UsuarioRepository;

public class RegisterUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public RegisterUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String execute(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Impossivel cadastrar dois usuários com o mesmo e-mail.");
        }

        usuarioRepository.save(usuario);
        return "Usuário cadastrado com sucesso.";
    }
}
