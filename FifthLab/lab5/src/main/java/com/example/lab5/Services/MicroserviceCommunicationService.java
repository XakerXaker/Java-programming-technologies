package com.example.lab5.Services;

import com.example.lab5.DTO.Color;
import com.example.lab5.DTO.OwnerDTO;
import com.example.lab5.DTO.OwnerRequest;
import com.example.lab5.DTO.PetDTO;
import com.example.lab5.DTO.PetRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MicroserviceCommunicationService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<OwnerDTO> getAllOwners() {
        OwnerRequest request = new OwnerRequest();
    
        request.setOperation("getAllOwners");

        Object response = rabbitTemplate.convertSendAndReceive("owner.exchange", "owner.request", request);
        return objectMapper.convertValue(response, new TypeReference<List<OwnerDTO>>(){});
    }


    public OwnerDTO getOwnerById(Long id) {
        OwnerRequest request = new OwnerRequest();
        request.setId(id);

        request.setOperation("getOwnerById");

        Object response = rabbitTemplate.convertSendAndReceive("owner.exchange", "owner.request", request);
        return (OwnerDTO) response;
    }

    public OwnerDTO createOwner(OwnerDTO ownerDto) {
        OwnerRequest request = new OwnerRequest();
        request.setId(ownerDto.getId());
        request.setName(ownerDto.getName());
        request.setBirthDate(ownerDto.getBirthDate());
        
        request.setOperation("createOwner");

        Object response = rabbitTemplate.convertSendAndReceive("owner.exchange", "owner.request", request);
        return objectMapper.convertValue(response, OwnerDTO.class);
    }

    public OwnerDTO updateOwner(Long id, OwnerDTO ownerDto) {
        OwnerRequest request = new OwnerRequest();
        request.setName(ownerDto.getName());
        request.setBirthDate(ownerDto.getBirthDate());
        
        request.setOperation("updateOwner");

        Object response = rabbitTemplate.convertSendAndReceive("owner.exchange", "owner.request", request);
        return objectMapper.convertValue(response, OwnerDTO.class);
    }

    public boolean deleteOwner(Long id) {
        OwnerRequest request = new OwnerRequest();
        request.setId(id);
    
        request.setOperation("deleteOwner");

        Object response = rabbitTemplate.convertSendAndReceive("owner.exchange", "owner.request", request);
        return objectMapper.convertValue(response, boolean.class);
    }

    public List<OwnerDTO> findOwnerByName(String name) {
        OwnerRequest request = new OwnerRequest();
        request.setName(name);
    
        request.setOperation("findByName");

        Object response = rabbitTemplate.convertSendAndReceive("owner.exchange", "owner.request", request);
        return objectMapper.convertValue(response, new TypeReference<List<OwnerDTO>>(){});
    }


    public List<PetDTO> getAllPets() {
        PetRequest request = new PetRequest();
    
        request.setOperation("getAllPets");

        Object response = rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
        return objectMapper.convertValue(response, new TypeReference<List<PetDTO>>(){});
    }

    public PetDTO getPetById(Long id) {
        PetRequest request = new PetRequest();
        request.setId(id);
    
        request.setOperation("getPetById");

        Object response = rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
        return objectMapper.convertValue(response, PetDTO.class);
    }

    public PetDTO createPet(PetDTO petDto) {
        PetRequest request = new PetRequest();
        request.setName(petDto.getName());
        request.setBreed(petDto.getBreed());
        request.setColor(petDto.getColor());
        request.setOwnerId(petDto.getOwner());

        request.setOperation("createPet");

        Object response = rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
        return objectMapper.convertValue(response, PetDTO.class);
    }

    public PetDTO updatePet(Long id, PetDTO petDto) {
        PetRequest request = new PetRequest();
        request.setName(petDto.getName());
        request.setBreed(petDto.getBreed());
        request.setColor(petDto.getColor());
        request.setOwnerId(petDto.getOwner());

        request.setOperation("updatePet");

        Object response = rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
        return objectMapper.convertValue(response, PetDTO.class);
    }

    public boolean deletePet(Long id) {
        PetRequest request = new PetRequest();
        request.setId(id);
        request.setOperation("deletePet");

        Object response = rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
        return objectMapper.convertValue(response, boolean.class);
    }

    public List<PetDTO> findPetByName(String name) {
        PetRequest request = new PetRequest();
        request.setName(name);
    
        request.setOperation("findByName");

        Object response = rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
        return objectMapper.convertValue(response, new TypeReference<List<PetDTO>>(){});
    }

    public List<PetDTO> findPetByBreed(String breed) {
        PetRequest request = new PetRequest();
        request.setBreed(breed);
    
        request.setOperation("findByBreed");

        Object response = rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
        return objectMapper.convertValue(response, new TypeReference<List<PetDTO>>(){});
    }


    public List<PetDTO> findPetByColor(Color color) {
        PetRequest request = new PetRequest();
        request.setColor(color);
    
        request.setOperation("findByColor");

        Object response = rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
        return objectMapper.convertValue(response, new TypeReference<List<PetDTO>>(){});
    }

    public List<PetDTO> findPetByOwnerId(Long ownerId) {
        PetRequest request = new PetRequest();
        request.setOwnerId(ownerId);
    
        request.setOperation("findByOwnerId");

        Object response = rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
        return objectMapper.convertValue(response, new TypeReference<List<PetDTO>>(){});
    }

    public void addPetFriends(Long id1, Long id2) {
        PetRequest request = new PetRequest();
        request.setId1(id1);
        request.setId2(id2);
    
        request.setOperation("addFriend");

        rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
    }
}
