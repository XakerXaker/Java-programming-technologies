package com.example.demo.DTO;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.Models.Owner;

public class OwnerDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private List<PetDTO> pets;

    public OwnerDTO() {}

    public OwnerDTO(Long id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public static OwnerDTO fromEntity(Owner owner) {
        OwnerDTO dto = new OwnerDTO();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        dto.setBirthDate(owner.getBirthDate());
        
        if (owner.getPets() != null) {
            dto.setPets(owner.getPets().stream()
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

    public List<PetDTO> getPets() {
        return pets;
    }

    public void setPets(List<PetDTO> pets) {
        this.pets = pets;
    }
}