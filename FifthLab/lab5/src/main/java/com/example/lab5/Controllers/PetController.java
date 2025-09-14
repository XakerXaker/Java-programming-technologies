package com.example.lab5.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.lab5.DTO.Color;
import com.example.lab5.DTO.PetDTO;
import com.example.lab5.Services.MicroserviceCommunicationService;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final MicroserviceCommunicationService microserviceService;

    @Autowired
    public PetController(MicroserviceCommunicationService microserviceService) {
        this.microserviceService = microserviceService;
    }

    @PostMapping("/addFriend")
    public void addFriend(@RequestParam Long id1, @RequestParam Long id2) {
        microserviceService.addPetFriends(id1, id2);
    }

    @GetMapping("get")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<PetDTO> pets = microserviceService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable Long id) {
        PetDTO pet = microserviceService.getPetById(id);
        return pet != null ? ResponseEntity.ok(pet) : ResponseEntity.notFound().build();
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<PetDTO> createPet(@RequestBody PetDTO petDto) {
        PetDTO createdPet = microserviceService.createPet(petDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("@securityService.canModifyPet(#id)")
    public ResponseEntity<PetDTO> updatePet(@PathVariable Long id, @RequestBody PetDTO petDto) {
        PetDTO updatedPet = microserviceService.updatePet(id, petDto);
        return updatedPet != null ? ResponseEntity.ok(updatedPet) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("@securityService.canModifyPet(#id)")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        boolean deleted = microserviceService.deletePet(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("search/name")
    public ResponseEntity<List<PetDTO>> findByName(@RequestParam String name) {
        List<PetDTO> pets = microserviceService.findPetByName(name);
        return ResponseEntity.ok(pets);
    }


    @GetMapping("search/breed")
    public ResponseEntity<List<PetDTO>> findByBreed(@RequestParam String breed) {
        List<PetDTO> pets = microserviceService.findPetByBreed(breed);
        return ResponseEntity.ok(pets);
    }


    @GetMapping("search/color")
    public ResponseEntity<List<PetDTO>> findByColor(@RequestParam Color color) {
        List<PetDTO> pets = microserviceService.findPetByColor(color);
        return ResponseEntity.ok(pets);
    }


    @GetMapping("search/owner/{ownerId}")
    public ResponseEntity<List<PetDTO>> findByOwnerId(@PathVariable Long ownerId) {
        List<PetDTO> pets = microserviceService.findPetByOwnerId(ownerId);
        return ResponseEntity.ok(pets);
    }
}
