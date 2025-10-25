package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.domain.shared.Pagina;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;

import java.util.List;

public class ListUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public ListUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Pagina<Usuario> execute(int offset, int size) {
        List<Usuario> usuarios = usuarioRepository.findAll(offset, size);
        long totalItens = usuarioRepository.countAll();
        int totalPaginas = (int) Math.ceil((double) totalItens / size);

        return new Pagina<>(usuarios, totalPaginas);
    }
}
