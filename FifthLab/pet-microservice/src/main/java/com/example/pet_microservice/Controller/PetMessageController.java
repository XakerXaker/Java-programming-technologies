package com.example.pet_microservice.Controller;

import com.example.pet_microservice.DTO.PetDTO;
import com.example.pet_microservice.DTO.PetRequest;
import com.example.pet_microservice.Services.PetService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PetMessageController {

    @Autowired
    private PetService petService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "pet.request.queue")
    public void handlePetRequest(PetRequest request) {
        try {
            Object response = processRequest(request);
            rabbitTemplate.convertAndSend("pet.exchange", "pet.response", response);
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("pet.exchange", "pet.response", "Error: " + e.getMessage());
        }
    }

    private Object processRequest(PetRequest request) {
        switch (request.getOperation()) {
            case "getAllPets":
                return petService.getAllPets();
            
            case "getAllPetsPaginated":
                return petService.getAllPetsPaginated(
                    request.getPage(), 
                    request.getSize(), 
                    request.getSortBy() != null ? request.getSortBy() : "id"
                );
            
            case "getPetById":
                return petService.getPetById(request.getId());
            
            case "createPet":
                PetDTO petDto = new PetDTO();
                petDto.setName(request.getName());
                petDto.setBreed(request.getBreed());
                petDto.setColor(request.getColor());
                petDto.setOwnerId(request.getOwnerId());
                return petService.createPet(petDto);
            
            case "updatePet":
                PetDTO updateDto = new PetDTO();
                updateDto.setName(request.getName());
                updateDto.setBreed(request.getBreed());
                updateDto.setColor(request.getColor());
                updateDto.setOwnerId(request.getOwnerId());
                return petService.updatePet(request.getId(), updateDto);
            
            case "deletePet":
                return petService.deletePet(request.getId());
            
            case "findByName":
                return petService.findByName(request.getName());
            
            case "findByNamePaginated":
                return petService.findByNamePaginated(request.getName(), request.getPage(), request.getSize());
            
            case "findByBreed":
                return petService.findByBreed(request.getBreed());
            
            case "findByBreedPaginated":
                return petService.findByBreedPaginated(request.getBreed(), request.getPage(), request.getSize());
            
            case "findByColor":
                return petService.findByColor(request.getColor());
            
            case "findByColorPaginated":
                return petService.findByColorPaginated(request.getColor(), request.getPage(), request.getSize());
            
            case "findByOwnerId":
                return petService.findByOwnerId(request.getOwnerId());
            
            case "addFriend":
                petService.addBothFriends(request.getId1(), request.getId2());
                return "Friends added successfully";
            
            default:
                return "Unknown operation: " + request.getOperation();
        }
    }
}

