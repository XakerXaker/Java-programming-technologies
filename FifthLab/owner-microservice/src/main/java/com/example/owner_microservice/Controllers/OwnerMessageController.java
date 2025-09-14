package com.example.owner_microservice.Controllers;

import com.example.owner_microservice.DTO.OwnerDTO;
import com.example.owner_microservice.DTO.OwnerRequest;
import com.example.owner_microservice.Services.OwnerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OwnerMessageController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "owner.request.queue")
    public void handleOwnerRequest(OwnerRequest request) {
        try {
            Object response = processRequest(request);
            rabbitTemplate.convertAndSend("owner.exchange", "owner.response", response);
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("owner.exchange", "owner.response", "Error: " + e.getMessage());
        }
    }

    private Object processRequest(OwnerRequest request) {
        switch (request.getOperation()) {
            case "getAllOwners":
                return ownerService.getAllOwners();
            
            case "getAllOwnersPaginated":
                return ownerService.getAllOwnersPaginated(
                    request.getPage(), 
                    request.getSize(), 
                    request.getSortBy() != null ? request.getSortBy() : "id"
                );
            
            case "getOwnerById":
                return ownerService.getOwnerById(request.getId());
            
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

