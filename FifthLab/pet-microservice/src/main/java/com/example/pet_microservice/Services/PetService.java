package com.example.pet_microservice.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.pet_microservice.DTO.PetDTO;
import com.example.pet_microservice.Models.Color;
import com.example.pet_microservice.Models.Pet;
import com.example.pet_microservice.Repositories.PetRepository;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(PetDTO::fromEntity)
                .toList();
    }

    public Page<PetDTO> getAllPetsPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return petRepository.findAll(pageable)
                .map(PetDTO::fromEntity);
    }

    public Optional<PetDTO> getPetById(Long id) {
        return petRepository.findById(id)
                .map(PetDTO::fromEntity);
    }

    public PetDTO createPet(PetDTO petDto) {
        Pet pet = new Pet();
        pet.setName(petDto.getName());
        pet.setBirthDate(petDto.getBirthDate());
        pet.setBreed(petDto.getBreed());
        pet.setColor(petDto.getColor());
        pet.setOwnerId(petDto.getOwnerId());
        
        Pet savedPet = petRepository.save(pet);
        return PetDTO.fromEntity(savedPet);
    }

    public Optional<PetDTO> updatePet(Long id, PetDTO petDto) {
        return petRepository.findById(id)
                .map(existingPet -> {
                    existingPet.setName(petDto.getName());
                    existingPet.setBirthDate(petDto.getBirthDate());
                    existingPet.setBreed(petDto.getBreed());
                    existingPet.setColor(petDto.getColor());
                    existingPet.setOwnerId(petDto.getOwnerId());
                    
                    Pet savedPet = petRepository.save(existingPet);
                    return PetDTO.fromEntity(savedPet);
                });
    }

    public boolean deletePet(Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<PetDTO> findByName(String name) {
        return petRepository.findByNameContainingIgnoreCase(name).stream()
                .map(PetDTO::fromEntity)
                .toList();
    }

    public Page<PetDTO> findByNamePaginated(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return petRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(PetDTO::fromEntity);
    }

    public List<PetDTO> findByBreed(String breed) {
        return petRepository.findByBreedContainingIgnoreCase(breed).stream()
                .map(PetDTO::fromEntity)
                .toList();
    }

    public Page<PetDTO> findByBreedPaginated(String breed, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return petRepository.findByBreedContainingIgnoreCase(breed, pageable)
                .map(PetDTO::fromEntity);
    }

    public List<PetDTO> findByColor(Color color) {
        return petRepository.findByColor(color).stream()
                .map(PetDTO::fromEntity)
                .toList();
    }

    public Page<PetDTO> findByColorPaginated(Color color, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return petRepository.findByColor(color, pageable)
                .map(PetDTO::fromEntity);
    }

    public List<PetDTO> findByOwnerId(Long ownerId) {
        return petRepository.findByOwnerId(ownerId).stream()
                .map(PetDTO::fromEntity)
                .toList();
    }

    public void addBothFriends(Long id1, Long id2) {
        Optional<Pet> pet1 = petRepository.findById(id1);
        Optional<Pet> pet2 = petRepository.findById(id2);
        
        if (pet1.isPresent() && pet2.isPresent()) {
            pet1.get().addFriend(pet2.get());
            petRepository.save(pet1.get());
        }
    }
}

