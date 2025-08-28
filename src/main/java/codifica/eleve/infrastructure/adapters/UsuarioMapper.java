package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.infrastructure.persistence.usuario.UsuarioEntity;

public class UsuarioMapper {
    public UsuarioEntity toEntity(Usuario domain) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setEmail(domain.getEmail());
        entity.setSenha(domain.getSenha());
        return entity;
    }

    public Usuario toDomain(UsuarioEntity entity) {
        Usuario domain = new Usuario();
        domain.setId(entity.getId());
        domain.setNome(entity.getNome());
        domain.setEmail(entity.getEmail());
        domain.setSenha(entity.getSenha());
        return domain;
    }
}
