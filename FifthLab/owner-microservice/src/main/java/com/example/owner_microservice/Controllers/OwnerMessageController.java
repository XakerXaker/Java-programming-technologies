package com.example.owner_microservice.Controllers;

import com.example.owner_microservice.DTO.OwnerDTO;
import com.example.owner_microservice.DTO.OwnerRequest;
import com.example.owner_microservice.Services.OwnerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OwnerMessageController {

    @Autowired
    private OwnerService ownerService;

    @RabbitListener(queues = "owner.request.queue")
    public Object handleOwnerRequest(OwnerRequest request) {
        try {
            Object response = processRequest(request);
            return response;
        } catch (Exception e) {
           return null;
        }
    }

    private Object processRequest(OwnerRequest request) {
        switch (request.getOperation()) {
            case "getAllOwners":
                if (!ownerService.getAllOwners().isEmpty())
                    return ownerService.getAllOwners();
                return null;
            
            case "getAllOwnersPaginated":
                return ownerService.getAllOwnersPaginated(
                    request.getPage(), 
                    request.getSize(), 
                    request.getSortBy() != null ? request.getSortBy() : "id"
                );
            
            case "getOwnerById":
                if (ownerService.getOwnerById(request.getId()).isPresent())
                    return ownerService.getOwnerById(request.getId());
                return null;
            
            case "createOwner":
                OwnerDTO ownerDto = new OwnerDTO();
                ownerDto.setName(request.getName());
                ownerDto.setBirthDate(request.getBirthDate());
                return ownerService.createOwner(ownerDto);
            
            case "updateOwner":
                OwnerDTO updateDto = new OwnerDTO();
                updateDto.setName(request.getName());
                updateDto.setBirthDate(request.getBirthDate());
                return ownerService.updateOwner(request.getId(), updateDto);
            
            case "deleteOwner":
                return ownerService.deleteOwner(request.getId());
            
            case "findByName":
                return ownerService.getOwnerByName(request.getName());
            
            case "findByNamePaginated":
                return ownerService.findByNamePaginated(request.getName(), request.getPage(), request.getSize());
            
            default:
                return "Unknown operation: " + request.getOperation();
        }
    }
}

