package com.example.DTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.Models.Color;
import com.example.Models.Pet;

public class PetDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private Color color;
    private OwnerDTO owner;
    private List<PetDTO> friends;

    public PetDTO() {}

    public PetDTO(Long id, String name, LocalDate birthDate, String breed, Color color) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
    }

    public static PetDTO fromEntity(Pet pet) {
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setBreed(pet.getBreed());
        dto.setColor(pet.getColor());
        
        if (pet.getOwner() != null) {
            dto.setOwner(OwnerDTO.fromEntity(pet.getOwner()));
        }
        
        if (pet.getFriends() != null) {
            dto.setFriends(pet.getFriends().stream()
                    .map(PetDTO::fromEntity)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }

    public List<PetDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<PetDTO> friends) {
        this.friends = friends;
    }
}
