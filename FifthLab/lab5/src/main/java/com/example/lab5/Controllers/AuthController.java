package com.example.lab5.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lab5.Models.Role;
import com.example.lab5.Models.UserAccount;
import com.example.lab5.Services.UserAccountService;

record LoginRequest(String username, String password) {}
record CreateUserRequest(String username, String password, Role role, Long ownerId) {}

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserAccountService userAccountService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserAccountService userAccountService) {
        this.authenticationManager = authenticationManager;
        this.userAccountService = userAccountService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> createUser(@RequestBody CreateUserRequest request) {
        UserAccount account = userAccountService.createUser(
            request.username(), request.password(), request.role(), request.ownerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(account.getId());
    }
}
