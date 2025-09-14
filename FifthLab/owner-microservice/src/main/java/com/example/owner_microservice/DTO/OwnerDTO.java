package com.example.owner_microservice.DTO;

import java.time.LocalDate;

import com.example.owner_microservice.Models.Owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OwnerDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;

    public static OwnerDTO fromEntity(Owner owner) {
        OwnerDTO dto = new OwnerDTO();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        dto.setBirthDate(owner.getBirthDate());
        return dto;
    }
}
