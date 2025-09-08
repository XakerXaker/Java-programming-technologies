package com.example.demo.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.demo.Models.Pet;
import com.example.demo.Models.Role;
import com.example.demo.Models.User;
import com.example.demo.Repositories.PetRepository;
import com.example.demo.Repositories.UserRepository;

@Component
public class SecurityService {
    private final UserRepository userAccountRepository;
    private final PetRepository petRepository;
    
    @Autowired
    public SecurityService(UserRepository userAccountRepository, PetRepository petRepository) {
        this.userAccountRepository = userAccountRepository;
        this.petRepository = petRepository;
    }

    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        Optional<User> account = userAccountRepository.findByUsername(auth.getName());
        return account.map(a -> a.getRole() == Role.ADMIN).orElse(false);
    }

    public Optional<User> getCurrentAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return Optional.empty();

        return userAccountRepository.findByUsername(auth.getName());
    }

    public boolean canModifyPet(Long petId) {
        if (isAdmin()) return true;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        Optional<User> accountOpt = userAccountRepository.findByUsername(auth.getName());
        if (accountOpt.isEmpty()) return false;

        Optional<Pet> petOpt = petRepository.findById(petId);
        if (petOpt.isEmpty()) return false;

        Pet pet = petOpt.get();
        return pet.getOwner() != null && accountOpt.get().getOwner() != null
            && pet.getOwner().getId().equals(accountOpt.get().getOwner().getId());
    }

    public boolean canModifyOwner(Long ownerId) {
        if (isAdmin()) return true;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        Optional<User> accountOpt = userAccountRepository.findByUsername(auth.getName());
        return accountOpt.isPresent() && accountOpt.get().getOwner() != null
            && ownerId.equals(accountOpt.get().getOwner().getId());
    }
}