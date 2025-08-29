package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.application.ports.out.PasswordEncoderPort;
import codifica.eleve.core.application.ports.out.TokenPort;
import codifica.eleve.core.domain.shared.exceptions.UnauthorizedException;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;

import java.util.HashMap;
import java.util.Optional;

public class LoginUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenPort tokenPort;

    public LoginUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoderPort passwordEncoder, TokenPort tokenPort) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenPort = tokenPort;
    }

    public Object execute(Usuario usuario) {
        Optional<Usuario> foundUserOptional = usuarioRepository.findByEmail(usuario.getEmail().getEndereco());
        if (foundUserOptional.isEmpty() || !passwordEncoder.matches(usuario.getSenha().getValor(), foundUserOptional.get().getSenhaCodificada())) {
            throw new UnauthorizedException("Credenciais inválidas.");
        }

        var response = new HashMap<String, Object>();
        response.put("mensagem", "Acesso autorizado.");
        response.put("Token", tokenPort.generate(foundUserOptional.get().getEmail().getEndereco()));
        return response;
    }
}