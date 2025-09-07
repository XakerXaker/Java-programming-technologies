package com.example.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DTO.PetDTO;
import com.example.Models.Color;
import com.example.Models.Owner;
import com.example.Models.Pet;
import com.example.Repositories.OwnerRepository;
import com.example.Repositories.PetRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public PetService(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(PetDTO::fromEntity)
                .collect(Collectors.toList());
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

    public void addBothFriends(Long id1, Long id2) {
        Pet pet1 = petRepository.findById(id1).get();
        Pet pet2 = petRepository.findById(id2).get();
        
        //дружба в обе стороны
        pet1.addFriend(pet2);

        petRepository.save(pet1);
        petRepository.save(pet2);
    }


    public PetDTO createPet(PetDTO petDto) {
        Pet pet = new Pet();
        pet.setName(petDto.getName());
        pet.setBirthDate(petDto.getBirthDate());
        pet.setBreed(petDto.getBreed());
        pet.setColor(petDto.getColor());
        
        // Установить владельца если указан
        if (petDto.getOwner().getId() != null) {
            Optional<Owner> owner = ownerRepository.findById(petDto.getOwner().getId());
            owner.ifPresent(pet::setOwner);
        }
        
        Pet savedPet = petRepository.save(pet);
        return PetDTO.fromEntity(savedPet);
    }

    public Optional<PetDTO> updatePet(Long id, PetDTO petDto) {
        return petRepository.findById(id)
                .map(pet -> {
                    pet.setName(petDto.getName());
                    pet.setBirthDate(petDto.getBirthDate());
                    pet.setBreed(petDto.getBreed());
                    pet.setColor(petDto.getColor());
                    
                    if (petDto.getOwner().getId() != null) {
                        Optional<Owner> owner = ownerRepository.findById(petDto.getOwner().getId());
                        owner.ifPresent(pet::setOwner);
                    }
                    
                    return PetDTO.fromEntity(petRepository.save(pet));
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
                .collect(Collectors.toList());
    }

    public Page<PetDTO> findByNamePaginated(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return petRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(PetDTO::fromEntity);
    }

    public List<PetDTO> findByBreed(String breed) {
        return petRepository.findByBreedContainingIgnoreCase(breed).stream()
                .map(PetDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<PetDTO> findByBreedPaginated(String breed, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return petRepository.findByBreedContainingIgnoreCase(breed, pageable)
                .map(PetDTO::fromEntity);
    }

    public List<PetDTO> findByColor(Color color) {
        return petRepository.findByColor(color).stream()
                .map(PetDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<PetDTO> findByColorPaginated(Color color, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return petRepository.findByColor(color, pageable)
                .map(PetDTO::fromEntity);
    }


    public List<PetDTO> findByOwnerId(Long ownerId) {
        return petRepository.findByOwnerId(ownerId).stream()
                .map(PetDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
