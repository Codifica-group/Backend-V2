package codifica.eleve.interfaces.controller;

import codifica.eleve.application.usecase.pet.*;
import codifica.eleve.domain.pet.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<PetDTO> create(@RequestBody PetDTO petDTO) {
        Pet pet = createPetUseCase.execute(petDtoMapper.toDomain(petDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(petDtoMapper.toDto(pet));
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> list() {
        List<Pet> pets = listPetUseCase.execute();
        if (pets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pets.stream().map(petDtoMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> findById(@PathVariable Integer id) {
        Pet pet = findPetByIdUseCase.execute(id);
        return ResponseEntity.ok(petDtoMapper.toDto(pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> update(@PathVariable Integer id, @RequestBody PetDTO petDTO) {
        Pet pet = updatePetUseCase.execute(id, petDtoMapper.toDomain(petDTO));
        return ResponseEntity.ok(petDtoMapper.toDto(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deletePetUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
