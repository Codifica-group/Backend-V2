package codifica.eleve.infrastructure.persistence.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Integer> {
    UsuarioEntity findByEmail(String email);
    UsuarioEntity findByEmailAndSenha(String email, String senha);
}
