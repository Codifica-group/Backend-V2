package codifica.eleve.infrastructure.persistence.pet;

import codifica.eleve.domain.pet.Pet;
import codifica.eleve.domain.pet.PetRepository;
import codifica.eleve.infrastructure.adapters.PetMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PetRepositoryImpl implements PetRepository {

    private final PetJpaRepository petJpaRepository;
    private final PetMapper petMapper;

    public PetRepositoryImpl(PetJpaRepository petJpaRepository, PetMapper petMapper) {
        this.petJpaRepository = petJpaRepository;
        this.petMapper = petMapper;
    }

    @Override
    public Pet save(Pet pet) {
        PetEntity entity = petMapper.toEntity(pet);
        PetEntity saved = petJpaRepository.save(entity);
        return petMapper.toDomain(saved);
    }

    @Override
    public Optional<Pet> findById(Integer id) {
        return petJpaRepository.findById(id).map(petMapper::toDomain);
    }

    @Override
    public List<Pet> findAll() {
        return petJpaRepository.findAll().stream().map(petMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        petJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return petJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNomeAndClienteId(String nome, Integer clienteId) {
        return petJpaRepository.existsByNomeAndClienteId(nome, clienteId);
    }
}