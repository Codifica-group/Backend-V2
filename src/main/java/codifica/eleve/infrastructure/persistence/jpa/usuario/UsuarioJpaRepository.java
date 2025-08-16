package codifica.eleve.infrastructure.persistence.jpa.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, Integer> {
    UsuarioJpaEntity findByEmail(String email);
    UsuarioJpaEntity findByEmailAndSenha(String email, String senha);
}
