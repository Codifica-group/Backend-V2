package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.application.ports.out.PasswordEncoderPort;
import codifica.eleve.core.domain.shared.Senha;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;

import java.util.HashMap;
import java.util.Map;

public class RegisterUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoderPort passwordEncoderPort;

    public RegisterUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoderPort passwordEncoderPort) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    public Map<String, Object> execute(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
        }

        if (!Senha.isValid(usuario.getSenha().getValor())) {
            throw new IllegalArgumentException("A senha é inválida. Deve ter no mínimo 8 caracteres, uma letra maiúscula, uma minúscula e um número.");
        }

        if (usuarioRepository.findByEmail(usuario.getEmail().getEndereco()).isPresent()) {
            throw new ConflictException("Impossível cadastrar dois usuários com o mesmo e-mail.");
        }

        String senhaCodificada = passwordEncoderPort.encode(usuario.getSenha().getValor());
        usuario.setSenhaCodificada(senhaCodificada);

        Usuario newUser = usuarioRepository.save(usuario);
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Usuário cadastrado com sucesso.");
        response.put("id", newUser.getId().getValue());
        return response;
    }
}
