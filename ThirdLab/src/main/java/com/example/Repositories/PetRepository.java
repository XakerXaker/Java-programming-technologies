package com.example.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Models.Color;
import com.example.Models.Pet;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByNameContainingIgnoreCase(String name);
    
    List<Pet> findByBreedContainingIgnoreCase(String breed);
    
    List<Pet> findByColor(Color color);
    
    List<Pet> findByOwnerId(Long ownerId);
    
    Page<Pet> findAll(Pageable pageable);
    
    Page<Pet> findByColor(Color color, Pageable pageable);
    
    Page<Pet> findByBreedContainingIgnoreCase(String breed, Pageable pageable);
    
    Page<Pet> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    Page<Pet> findByColorAndBreedContainingIgnoreCase(Color color, String breed, Pageable pageable);
}
