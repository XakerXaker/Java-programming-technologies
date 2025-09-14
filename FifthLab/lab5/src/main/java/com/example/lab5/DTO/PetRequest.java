package com.example.lab5.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PetRequest {
    private String operation;

    private Long id;
    private String name;
    private String breed;
    private Color color;

    private Long ownerId;

    private Long id1;
    private Long id2;

    private int page;
    private int size;
    private String sortBy;
}

