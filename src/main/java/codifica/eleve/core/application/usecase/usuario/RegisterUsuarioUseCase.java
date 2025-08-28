package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.application.ports.out.PasswordEncoderPort;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;

public class RegisterUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoderPort passwordEncoderPort;

    public RegisterUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoderPort passwordEncoderPort) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    public String execute(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ConflictException("Impossivel cadastrar dois usuários com o mesmo e-mail.");
        }

        usuario.setSenha(passwordEncoderPort.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return "Usuário cadastrado com sucesso.";
    }
}
