package com.example.lab5.Services;

import com.example.lab5.DTO.Color;
import com.example.lab5.DTO.OwnerDTO;
import com.example.lab5.DTO.OwnerRequest;
import com.example.lab5.DTO.PetDTO;
import com.example.lab5.DTO.PetRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MicroserviceCommunicationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<OwnerDTO> getAllOwners() {
        OwnerRequest request = new OwnerRequest();
    
        request.setOperation("getAllOwners");

        List<OwnerDTO> response = rabbitTemplate.convertSendAndReceiveAsType("owner.exchange", "owner.request", request, new ParameterizedTypeReference<List<OwnerDTO>>() {
        });
        return response;
    }


    public OwnerDTO getOwnerById(Long id) {
        OwnerRequest request = new OwnerRequest();
        request.setId(id);

        request.setOperation("getOwnerById");

        OwnerDTO response = rabbitTemplate.convertSendAndReceiveAsType("owner.exchange", "owner.request", request, new ParameterizedTypeReference<OwnerDTO>() {     
        });
        return response;
    }

    public OwnerDTO createOwner(OwnerDTO ownerDto) {
        OwnerRequest request = new OwnerRequest();
        request.setId(ownerDto.getId());
        request.setName(ownerDto.getName());
        request.setBirthDate(ownerDto.getBirthDate());
        
        request.setOperation("createOwner");

        OwnerDTO response = rabbitTemplate.convertSendAndReceiveAsType("owner.exchange", "owner.request", request, new ParameterizedTypeReference<OwnerDTO>() {    
        });
        return response;
    }

    public OwnerDTO updateOwner(Long id, OwnerDTO ownerDto) {
        OwnerRequest request = new OwnerRequest();
        request.setName(ownerDto.getName());
        request.setBirthDate(ownerDto.getBirthDate());
        
        request.setOperation("updateOwner");

        OwnerDTO response = rabbitTemplate.convertSendAndReceiveAsType("owner.exchange", "owner.request", request, new ParameterizedTypeReference<OwnerDTO>() {
        });
        return response;
    }

    public boolean deleteOwner(Long id) {
        OwnerRequest request = new OwnerRequest();
        request.setId(id);
    
        request.setOperation("deleteOwner");

        Object response = rabbitTemplate.convertSendAndReceive("owner.exchange", "owner.request", request);
        return (boolean) response;
    }

    public List<OwnerDTO> findOwnerByName(String name) {
        OwnerRequest request = new OwnerRequest();
        request.setName(name);
    
        request.setOperation("findByName");

        List<OwnerDTO> response = rabbitTemplate.convertSendAndReceiveAsType("owner.exchange", "owner.request", request, new ParameterizedTypeReference<List<OwnerDTO>>() {
        });
        return response;
    }


    public List<PetDTO> getAllPets() {
        PetRequest request = new PetRequest();
    
        request.setOperation("getAllPets");

        List<PetDTO> response = rabbitTemplate.convertSendAndReceiveAsType("pet.exchange", "pet.request", request, new ParameterizedTypeReference<List<PetDTO>>() {
        });
        return response;
    }

    public PetDTO getPetById(Long id) {
        PetRequest request = new PetRequest();
        request.setId(id);
    
        request.setOperation("getPetById");

        PetDTO response = rabbitTemplate.convertSendAndReceiveAsType("pet.exchange", "pet.request", request, new ParameterizedTypeReference<PetDTO>() {
        });
        return response;
    }

    public PetDTO createPet(PetDTO petDto) {
        PetRequest request = new PetRequest();
        request.setName(petDto.getName());
        request.setBreed(petDto.getBreed());
        request.setColor(petDto.getColor());
        request.setOwnerId(petDto.getOwnerId());

        request.setOperation("createPet");

        PetDTO response = rabbitTemplate.convertSendAndReceiveAsType("pet.exchange", "pet.request", request, new ParameterizedTypeReference<PetDTO>() {
        });
        return response;
    }

    public PetDTO updatePet(Long id, PetDTO petDto) {
        PetRequest request = new PetRequest();
        request.setName(petDto.getName());
        request.setBreed(petDto.getBreed());
        request.setColor(petDto.getColor());
        request.setOwnerId(petDto.getOwnerId());

        request.setOperation("updatePet");

        PetDTO response = rabbitTemplate.convertSendAndReceiveAsType("pet.exchange", "pet.request", request, new ParameterizedTypeReference<PetDTO>() {
        });
        return response;
    }

    public boolean deletePet(Long id) {
        PetRequest request = new PetRequest();
        request.setId(id);
        request.setOperation("deletePet");

        Object response = rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
        return (boolean) response;
    }

    public List<PetDTO> findPetByName(String name) {
        PetRequest request = new PetRequest();
        request.setName(name);
    
        request.setOperation("findByName");

        List<PetDTO> response = rabbitTemplate.convertSendAndReceiveAsType("pet.exchange", "pet.request", request, new ParameterizedTypeReference<List<PetDTO>>() {
        });
        return response;
    }

    public List<PetDTO> findPetByBreed(String breed) {
        PetRequest request = new PetRequest();
        request.setBreed(breed);
    
        request.setOperation("findByBreed");

        List<PetDTO> response = rabbitTemplate.convertSendAndReceiveAsType("pet.exchange", "pet.request", request, new ParameterizedTypeReference<List<PetDTO>>() {
        });
        return response;
    }


    public List<PetDTO> findPetByColor(Color color) {
        PetRequest request = new PetRequest();
        request.setColor(color);
    
        request.setOperation("findByColor");

        List<PetDTO> response = rabbitTemplate.convertSendAndReceiveAsType("pet.exchange", "pet.request", request, new ParameterizedTypeReference<List<PetDTO>>() {
        });
        return response;
    }

    public List<PetDTO> findPetByOwnerId(Long ownerId) {
        PetRequest request = new PetRequest();
        request.setOwnerId(ownerId);
    
        request.setOperation("findByOwnerId");

        List<PetDTO> response = rabbitTemplate.convertSendAndReceiveAsType("pet.exchange", "pet.request", request, new ParameterizedTypeReference<List<PetDTO>>() {
        });
        return response;
    }

    public void addPetFriends(Long id1, Long id2) {
        PetRequest request = new PetRequest();
        request.setId1(id1);
        request.setId2(id2);
    
        request.setOperation("addFriend");

        rabbitTemplate.convertSendAndReceive("pet.exchange", "pet.request", request);
    }
}
