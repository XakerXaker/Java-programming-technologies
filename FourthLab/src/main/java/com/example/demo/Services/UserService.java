package com.example.demo.Services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Models.Owner;
import com.example.demo.Models.Role;
import com.example.demo.Models.User;
import com.example.demo.Repositories.OwnerRepository;
import com.example.demo.Repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userAccountRepository;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userAccountRepository,
                              OwnerRepository ownerRepository,
                              PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    @Transactional
    public User createUser(String username, String rawPassword, Role role, Long ownerId) {
        User account = new User();

        account.setUsername(username);
        account.setPasswordHash(passwordEncoder.encode(rawPassword));
        account.setRole(role);

        if (ownerId != null) {
            Owner owner = ownerRepository.findById(ownerId).orElseThrow();
            account.setOwner(owner);
        }
        
        return userAccountRepository.save(account);
    }
}


