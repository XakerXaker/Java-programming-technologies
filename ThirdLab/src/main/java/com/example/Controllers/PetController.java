package com.example.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.DTO.PetDTO;
import com.example.Models.Color;
import com.example.Services.PetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;


    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<PetDTO> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("paginated")
    public ResponseEntity<Page<PetDTO>> getAllPetsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<PetDTO> pets = petService.getAllPetsPaginated(page, size, sortBy);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable Long id) {
        Optional<PetDTO> pet = petService.getPetById(id);
        return pet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<PetDTO> createPet(PetDTO petDto) {
        PetDTO createdPet = petService.createPet(petDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetDTO> updatePet(@PathVariable Long id, @RequestParam("Pet") PetDTO petDto) {
        Optional<PetDTO> updatedPet = petService.updatePet(id, petDto);
        return updatedPet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        boolean deleted = petService.deletePet(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<PetDTO>> findByName(@RequestParam("Name") String name) {
        List<PetDTO> pets = petService.findByName(name);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/search/name/paginated")
    public ResponseEntity<Page<PetDTO>> findByNamePaginated(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PetDTO> pets = petService.findByNamePaginated(name, page, size);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/search/breed")
    public ResponseEntity<List<PetDTO>> findByBreed(@RequestParam String breed) {
        List<PetDTO> pets = petService.findByBreed(breed);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/search/breed/paginated")
    public ResponseEntity<Page<PetDTO>> findByBreedPaginated(
            @RequestParam String breed,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PetDTO> pets = petService.findByBreedPaginated(breed, page, size);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/search/color")
    public ResponseEntity<List<PetDTO>> findByColor(@RequestParam Color color) {
        List<PetDTO> pets = petService.findByColor(color);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/search/color/paginated")
    public ResponseEntity<Page<PetDTO>> findByColorPaginated(
            @RequestParam Color color,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PetDTO> pets = petService.findByColorPaginated(color, page, size);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/search/owner/{ownerId}")
    public ResponseEntity<List<PetDTO>> findByOwnerId(@PathVariable Long ownerId) {
        List<PetDTO> pets = petService.findByOwnerId(ownerId);
        return ResponseEntity.ok(pets);
    }
}
