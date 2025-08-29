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
        if (usuarioRepository.findByEmail(usuario.getEmail().getEndereco()).isPresent()) {
            throw new ConflictException("Impossível cadastrar dois usuários com o mesmo e-mail.");
        }

        String senhaCodificada = passwordEncoderPort.encode(usuario.getSenha().getValor());
        usuario.setSenhaCodificada(senhaCodificada);

        usuarioRepository.save(usuario);
        return "Usuário cadastrado com sucesso.";
    }
}
