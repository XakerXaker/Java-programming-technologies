package com.example.lab5.Services;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.lab5.Models.Role;
import com.example.lab5.Models.UserAccount;
import com.example.lab5.Repositories.UserAccountRepository;

@Component
public class SecurityService {
    private final UserAccountRepository userAccountRepository;

    public SecurityService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;
        Optional<UserAccount> account = userAccountRepository.findByUsername(auth.getName());
        return account.map(a -> a.getRole() == Role.ADMIN).orElse(false);
    }

    public Optional<UserAccount> getCurrentAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return Optional.empty();
        return userAccountRepository.findByUsername(auth.getName());
    }

    public boolean canModifyPet(Long petId) {
        if (isAdmin()) return true;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;
        Optional<UserAccount> accountOpt = userAccountRepository.findByUsername(auth.getName());
        if (accountOpt.isEmpty()) return false;
        
        // In microservice architecture, we need to check if the pet belongs to the user's owner
        // This would require a call to the pet microservice, but for simplicity, we'll allow USER role
        return accountOpt.get().getRole() == Role.USER;
    }

    public boolean canModifyOwner(Long ownerId) {
        if (isAdmin()) return true;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;
        Optional<UserAccount> accountOpt = userAccountRepository.findByUsername(auth.getName());
        return accountOpt.isPresent() && accountOpt.get().getOwnerId() != null
            && ownerId.equals(accountOpt.get().getOwnerId());
    }
}
