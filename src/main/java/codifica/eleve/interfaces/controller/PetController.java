package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.pet.*;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.interfaces.adapters.PetDtoMapper;
import codifica.eleve.interfaces.dto.PetDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final CreatePetUseCase createPetUseCase;
    private final ListPetUseCase listPetUseCase;
    private final FindPetByIdUseCase findPetByIdUseCase;
    private final UpdatePetUseCase updatePetUseCase;
    private final DeletePetUseCase deletePetUseCase;
    private final PetDtoMapper petDtoMapper;

    public PetController(CreatePetUseCase createPetUseCase, ListPetUseCase listPetUseCase,
                         FindPetByIdUseCase findPetByIdUseCase, UpdatePetUseCase updatePetUseCase,
                         DeletePetUseCase deletePetUseCase, PetDtoMapper petDtoMapper) {
        this.createPetUseCase = createPetUseCase;
        this.listPetUseCase = listPetUseCase;
        this.findPetByIdUseCase = findPetByIdUseCase;
        this.updatePetUseCase = updatePetUseCase;
        this.deletePetUseCase = deletePetUseCase;
        this.petDtoMapper = petDtoMapper;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody PetDTO petDTO) {
        Object json = createPetUseCase.execute(petDtoMapper.toDomain(petDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(json);
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> list() {
        List<Pet> pets = listPetUseCase.execute();
        return pets.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> findById(@PathVariable Integer id) {
        Pet pet = findPetByIdUseCase.execute(id);
        return ResponseEntity.ok(petDtoMapper.toDto(pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody PetDTO petDTO) {
        String message = updatePetUseCase.execute(id, petDtoMapper.toDomain(petDTO));
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        deletePetUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
