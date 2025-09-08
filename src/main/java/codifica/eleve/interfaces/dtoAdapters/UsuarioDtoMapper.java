package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.shared.Email;
import codifica.eleve.core.domain.shared.Senha;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.interfaces.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDtoMapper {

    public Usuario toDomain(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Email email = new Email(dto.getEmail());
        Senha senha = new Senha(dto.getSenha());

        return new Usuario(dto.getNome(), email, senha);
    }

    public UsuarioDTO toDto(Usuario domain) {
        if (domain == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        if(domain.getId() != null) {
            dto.setId(domain.getId().getValue());
        }
        dto.setNome(domain.getNome());
        dto.setEmail(domain.getEmail().getEndereco());
        return dto;
    }
}
