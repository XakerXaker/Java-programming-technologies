package com.example.demo.Controllers;

import com.example.demo.DTO.OwnerDTO;
import com.example.demo.Services.OwnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("get")
    public ResponseEntity<List<OwnerDTO>> getAllOwners() {
        List<OwnerDTO> owners = ownerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @GetMapping("get/paginated")
    public ResponseEntity<Page<OwnerDTO>> getAllOwnersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<OwnerDTO> owners = ownerService.getAllOwnersPaginated(page, size, sortBy);
        return ResponseEntity.ok(owners);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable Long id) {
        Optional<OwnerDTO> owner = ownerService.getOwnerById(id);
        return owner.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerDTO> createOwner(@RequestBody OwnerDTO ownerDto) {
        OwnerDTO createdOwner = ownerService.createOwner(ownerDto);
        return ResponseEntity.ok(createdOwner);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("@securityService.canModifyOwner(#id)")
    public ResponseEntity<OwnerDTO> updateOwner(@PathVariable Long id, @RequestBody OwnerDTO ownerDto) {
        Optional<OwnerDTO> updatedOwner = ownerService.updateOwner(id, ownerDto);
        return updatedOwner.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        boolean deleted = ownerService.deleteOwner(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("search/name")
    public ResponseEntity<List<OwnerDTO>> findByName(@RequestParam String name) {
        List<OwnerDTO> owners = ownerService.getOwnerByName(name);
        return ResponseEntity.ok(owners);
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