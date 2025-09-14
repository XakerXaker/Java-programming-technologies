package com.example.owner_microservice.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.owner_microservice.DTO.OwnerDTO;
import com.example.owner_microservice.Models.Owner;
import com.example.owner_microservice.Repositories.OwnerRepository;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<OwnerDTO> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(OwnerDTO::fromEntity)
                .toList();
    }

    public Page<OwnerDTO> getAllOwnersPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ownerRepository.findAll(pageable)
                .map(OwnerDTO::fromEntity);
    }

    public Optional<OwnerDTO> getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .map(OwnerDTO::fromEntity);
    }

    public OwnerDTO createOwner(OwnerDTO ownerDto) {
        Owner owner = new Owner();
        owner.setName(ownerDto.getName());
        owner.setBirthDate(ownerDto.getBirthDate());
        
        Owner savedOwner = ownerRepository.save(owner);
        return OwnerDTO.fromEntity(savedOwner);
    }

    public Optional<OwnerDTO> updateOwner(Long id, OwnerDTO ownerDto) {
        return ownerRepository.findById(id)
                .map(existingOwner -> {
                    existingOwner.setName(ownerDto.getName());
                    existingOwner.setBirthDate(ownerDto.getBirthDate());
                    
                    Owner savedOwner = ownerRepository.save(existingOwner);
                    return OwnerDTO.fromEntity(savedOwner);
                });
    }

    public boolean deleteOwner(Long id) {
        if (ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<OwnerDTO> getOwnerByName(String name) {
        return ownerRepository.findByNameContainingIgnoreCase(name).stream()
                .map(OwnerDTO::fromEntity)
                .toList();
    }

    public Page<OwnerDTO> findByNamePaginated(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ownerRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(OwnerDTO::fromEntity);
    }
}
