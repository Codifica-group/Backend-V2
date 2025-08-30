package codifica.eleve.core.domain.raca.porte;

import java.util.Optional;

public interface PorteRepository {
    Optional<Porte> findById(Integer id);
}
