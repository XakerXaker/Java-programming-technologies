package com.example.pet_microservice.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.pet_microservice.Models.Color;
import com.example.pet_microservice.Models.Pet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {
    private Long id;

    private String name;
    private LocalDate birthDate;
    private String breed;
    private Color color;

    private Long ownerId;

    private List<PetDTO> friends = new ArrayList<>();


    public static PetDTO fromEntity(Pet pet) {
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setBreed(pet.getBreed());
        dto.setColor(pet.getColor());
        dto.setOwnerId(pet.getOwnerId());
        
        if (pet.getFriends() != null) {
            dto.setFriends(pet.getFriends().stream()
                    .map(PetDTO::fromEntity)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
}
