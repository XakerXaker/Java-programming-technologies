package com.example.Controllers;

import com.example.DTO.OwnerDTO;
import com.example.Services.OwnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable Long id) {
        Optional<OwnerDTO> owner = ownerService.getOwnerById(id);
        return owner.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @PostMapping("create")
    public ResponseEntity<OwnerDTO> createOwner(OwnerDTO ownerDto) {
        OwnerDTO createdOwner = ownerService.createOwner(ownerDto);
        return ResponseEntity.ok(createdOwner);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<OwnerDTO> updateOwner(@PathVariable Long id, OwnerDTO ownerDto) {
        Optional<OwnerDTO> updatedOwner = ownerService.updateOwner(id, ownerDto);
        return updatedOwner.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        boolean deleted = ownerService.deleteOwner(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("search/name")
    public List<OwnerDTO> findByName(@RequestParam("Name") String name) {
        List<OwnerDTO> owners = ownerService.getOwnerByName(name);
        return owners;
    }

    @GetMapping("search/name/paginated")
    public ResponseEntity<Page<OwnerDTO>> findByNamePaginated(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OwnerDTO> owners = ownerService.findByNamePaginated(name, page, size);
        return ResponseEntity.ok(owners);
    }
}