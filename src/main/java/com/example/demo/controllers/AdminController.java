package com.example.demo.controllers;

import com.example.demo.User;
import com.example.demo.entities.Participant;
import com.example.demo.dto.CreateUserRequestDTO;
import com.example.demo.dto.UpdateUserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AuthService authService;

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin() {
        return "Hello, Admin!";
    }

    // Get all users
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(u -> ResponseEntity.ok(new UserResponseDTO(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new user
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequestDTO request) {
        try {
            User user = authService.register(
                request.getName(), 
                request.getEmail(), 
                request.getPassword(), 
                request.getRole(), 
                request.getParticipantId()
            );
            return ResponseEntity.ok(new UserResponseDTO(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update user
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequestDTO request) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            boolean nameChanged = false;
            boolean emailChanged = false;
            if (request.getName() != null) {
                user.setName(request.getName());
                nameChanged = true;
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
                emailChanged = true;
            }
            if (request.getRole() != null) user.setRole(request.getRole());

            // Update linked participant if present
            if (user.getParticipant() != null && (nameChanged || emailChanged)) {
                Participant participant = user.getParticipant();
                if (nameChanged) {
                    String[] nameParts = user.getName().trim().split("\\s+", 2);
                    if (nameParts.length >= 2) {
                        participant.setFirstName(nameParts[0]);
                        participant.setLastName(nameParts[1]);
                    } else {
                        participant.setFirstName(user.getName());
                        participant.setLastName("");
                    }
                }
                if (emailChanged) {
                    participant.setEmail(user.getEmail());
                }
            }

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(new UserResponseDTO(updatedUser));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
} 