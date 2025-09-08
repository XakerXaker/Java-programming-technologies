package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Owner;


@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    List<Owner> findByName(String name);
    
    Page<Owner> findAll(Pageable pageable);
    
    Page<Owner> findByNameContainingIgnoreCase(String name, Pageable pageable);
}