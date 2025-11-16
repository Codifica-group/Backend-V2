package codifica.eleve.infrastructure.cache;

import codifica.eleve.core.application.usecase.pet.*;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.Pagina;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Map;

public class PetCachingDecorators {

    @Component
    public static class CachingListPetUseCase extends ListPetUseCase {
        public CachingListPetUseCase(PetRepository petRepository) {
            super(petRepository);
        }

        @Override
        @Cacheable(cacheNames = "pets", key = "#offset + '-' + #size")
        public Pagina<Pet> execute(int offset, int size) {
            return super.execute(offset, size);
        }
    }

    @Component
    public static class CachingCreatePetUseCase extends CreatePetUseCase {
        public CachingCreatePetUseCase(PetRepository petRepository) {
            super(petRepository);
        }

        @Override
        @CacheEvict(cacheNames = "pets", allEntries = true)
        public Map<String, Object> execute(Pet pet) {
            return super.execute(pet);
        }
    }

    @Component
    public static class CachingUpdatePetUseCase extends UpdatePetUseCase {
        public CachingUpdatePetUseCase(PetRepository petRepository) {
            super(petRepository);
        }

        @Override
        @CacheEvict(cacheNames = "pets", allEntries = true)
        public String execute(Integer id, Pet pet) {
            return super.execute(id, pet);
        }
    }

    @Component
    public static class CachingDeletePetUseCase extends DeletePetUseCase {
        public CachingDeletePetUseCase(PetRepository petRepository, AgendaRepository agendaRepository) {
            super(petRepository, agendaRepository);
        }

        @Override
        @CacheEvict(cacheNames = "pets", allEntries = true)
        public void execute(Integer id) {
            super.execute(id);
        }
    }
}
