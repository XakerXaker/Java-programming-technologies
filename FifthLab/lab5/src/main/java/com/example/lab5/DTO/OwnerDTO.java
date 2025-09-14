package com.example.lab5.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OwnerDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private List<PetDTO> pets;
}

