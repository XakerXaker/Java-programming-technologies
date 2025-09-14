package com.example.lab5.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OwnerRequest {
    private String operation;

    private Long id;
    private String name;
    private LocalDate birthDate;
    
    private int page;
    private int size;
    private String sortBy;
}
