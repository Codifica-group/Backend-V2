package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.shared.Email;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.infrastructure.persistence.usuario.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioEntity toEntity(Usuario domain) {
        UsuarioEntity entity = new UsuarioEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }
        entity.setNome(domain.getNome());
        entity.setEmail(domain.getEmail().getEndereco());
        entity.setSenha(domain.getSenhaCodificada());
        return entity;
    }

    public Usuario toDomain(UsuarioEntity entity) {
        Usuario domain = new Usuario();
        domain.setId(new Id(entity.getId()));
        domain.setNome(entity.getNome());
        domain.setEmail(new Email(entity.getEmail()));
        domain.setSenhaCodificada(entity.getSenha());
        domain.setSenha(null);
        return domain;
    }
}
