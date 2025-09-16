package com.example.lab5.Controllers;

import com.example.lab5.DTO.OwnerDTO;
import com.example.lab5.Services.MicroserviceCommunicationService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final MicroserviceCommunicationService microserviceService;

    @Autowired
    public OwnerController(MicroserviceCommunicationService microserviceService) {
        this.microserviceService = microserviceService;
    }

    @GetMapping("get")
    public ResponseEntity<List<OwnerDTO>> getAllOwners() {
        List<OwnerDTO> owners = microserviceService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable Long id) {
        OwnerDTO owner = microserviceService.getOwnerById(id);
        return owner != null ? ResponseEntity.ok(owner) : ResponseEntity.noContent().build();
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerDTO> createOwner(@RequestBody OwnerDTO ownerDto) {
        OwnerDTO createdOwner = microserviceService.createOwner(ownerDto);
        return ResponseEntity.ok(createdOwner);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("@securityService.canModifyOwner(#id)")
    public ResponseEntity<OwnerDTO> updateOwner(@PathVariable Long id, @RequestBody OwnerDTO ownerDto) {
        OwnerDTO updatedOwner = microserviceService.updateOwner(id, ownerDto);
        return updatedOwner != null ? ResponseEntity.ok(updatedOwner) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        boolean deleted = microserviceService.deleteOwner(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("search/name")
    public ResponseEntity<List<OwnerDTO>> findByName(@RequestParam String name) {
        List<OwnerDTO> owners = microserviceService.findOwnerByName(name);
        return ResponseEntity.ok(owners);
    }
}
