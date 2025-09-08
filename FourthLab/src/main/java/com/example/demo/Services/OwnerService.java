package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.OwnerDTO;
import com.example.demo.Models.Owner;
import com.example.demo.Repositories.OwnerRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OwnerService {

    private final OwnerRepository ownerRepository;
    
    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<OwnerDTO> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(OwnerDTO::fromEntity)
                .collect(Collectors.toList());
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
    

    public List<OwnerDTO> getOwnerByName(String name) {
        return ownerRepository.findByName(name).stream()
                .map(OwnerDTO::fromEntity)
                .toList();
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
                .map(owner -> {
                    owner.setName(ownerDto.getName());
                    owner.setBirthDate(ownerDto.getBirthDate());
                    return OwnerDTO.fromEntity(ownerRepository.save(owner));
                });
    }

    public boolean deleteOwner(Long id) {
        if (ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<OwnerDTO> findByNamePaginated(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ownerRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(OwnerDTO::fromEntity);
    }
}