package codifica.eleve.core.application.usecase.pet;

import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.Pagina;

import java.util.List;

public class ListPetUseCase {
    private final PetRepository petRepository;

    public ListPetUseCase(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pagina<Pet> execute(int offset, int size) {
        List<Pet> pets = petRepository.findAll(offset, size);
        long totalItens = petRepository.countAll();
        int totalPaginas = (int) Math.ceil((double) totalItens / size);

        return new Pagina<>(pets, totalPaginas);
    }
}