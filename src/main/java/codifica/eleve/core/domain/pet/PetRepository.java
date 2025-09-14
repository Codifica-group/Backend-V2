package codifica.eleve.core.domain.pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    Pet save(Pet pet);
    Optional<Pet> findById(Integer id);
    List<Pet> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
    boolean existsByNomeAndClienteId(String nome, Integer clienteId);
    boolean existsByRacaId(Integer racaId);
    boolean existsByClienteId(Integer clienteId);
}
