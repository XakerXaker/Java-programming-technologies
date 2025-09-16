package com.example.pet_microservice.Controller;

import com.example.pet_microservice.DTO.PetDTO;
import com.example.pet_microservice.DTO.PetRequest;
import com.example.pet_microservice.Services.PetService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PetMessageController {
    @Autowired
    private PetService petService;

    @RabbitListener(queues = "pet.request.queue")
    public Object handlePetRequest(PetRequest request) {
        try {
            Object response = processRequest(request);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    private Object processRequest(PetRequest request) {
        switch (request.getOperation()) {
            case "getAllPets":
                if (petService.getAllPets().isEmpty())
                    return null;
                return petService.getAllPets();
            
            case "getPetById":
                return petService.getPetById(request.getId()).orElse(null);
            
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
                return petService.updatePet(request.getId(), updateDto).orElse(null);
            
            case "deletePet":
                return petService.deletePet(request.getId());
            
            case "findByName":
                if (petService.findByName(request.getName()).isEmpty())
                    return null;
                return petService.findByName(request.getName());
        
            
            case "findByBreed":
                if (petService.findByBreed(request.getBreed()).isEmpty())
                    return null;
                return petService.findByBreed(request.getBreed());
            
            case "findByColor":
                if (petService.findByColor(request.getColor()).isEmpty())
                    return null;
                return petService.findByColor(request.getColor());
            
            case "findByOwnerId":
                if (petService.findByOwnerId(request.getOwnerId()).isEmpty())
                    return null;
                return petService.findByOwnerId(request.getOwnerId());
            
            case "addFriend":
                petService.addBothFriends(request.getId1(), request.getId2());
                return "Friends added successfully";
            
            default:
                return "Unknown operation: " + request.getOperation();
        }
    }
}

