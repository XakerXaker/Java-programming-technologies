package com.example.pet_microservice.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pet_microservice.Models.Color;
import com.example.pet_microservice.Models.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
    List<Pet> findByNameContainingIgnoreCase(String name);
    Page<Pet> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    List<Pet> findByBreedContainingIgnoreCase(String breed);
    Page<Pet> findByBreedContainingIgnoreCase(String breed, Pageable pageable);
    
    List<Pet> findByColor(Color color);
    Page<Pet> findByColor(Color color, Pageable pageable);
    
    List<Pet> findByOwnerId(Long ownerId);
    Page<Pet> findByOwnerId(Long ownerId, Pageable pageable);
}
