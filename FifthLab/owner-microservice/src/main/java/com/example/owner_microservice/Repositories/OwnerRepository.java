package com.example.owner_microservice.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.owner_microservice.Models.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findByNameContainingIgnoreCase(String name);
    Page<Owner> findByNameContainingIgnoreCase(String name, Pageable pageable);
}