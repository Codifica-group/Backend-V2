package codifica.eleve.application.usecase.usuario;

import codifica.eleve.domain.usuario.Usuario;
import codifica.eleve.domain.usuario.UsuarioRepository;

public class LoginUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public LoginUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String execute(Usuario usuario) {
        if (usuarioRepository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha()).isPresent()) {
            return "Acesso autorizado.";
        } else {
            throw new RuntimeException("Credenciais inválidas.");
        }
    }
}
