package com.example.pet_microservice.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Fetch;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pet {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private LocalDate birthDate;
    private String breed;
    
    private Long ownerId; 

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "pet_friends",
        joinColumns = @JoinColumn(name = "pet_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    
    private List<Pet> friends = new ArrayList<>();

    public void addFriend(Pet pet) {
        friends.add(pet);
        pet.addFriend(this);
    }
}

