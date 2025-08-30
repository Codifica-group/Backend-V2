package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.application.ports.out.PasswordEncoderPort;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class UpdateUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;

    public UpdateUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoderPort passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String execute(Integer id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());

        if (usuario.getSenha() != null && usuario.getSenha().getValor() != null && !usuario.getSenha().getValor().isEmpty()) {
            String senhaCodificada = passwordEncoder.encode(usuario.getSenha().getValor());
            usuarioExistente.setSenhaCodificada(senhaCodificada);
        }

        usuarioRepository.save(usuarioExistente);
        return "Usuário atualizado com sucesso.";
    }
}
