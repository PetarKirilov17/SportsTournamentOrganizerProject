package com.example.demo.services;

import com.example.demo.User;
import com.example.demo.enums.Role;
import com.example.demo.repositories.UserRepository;
import com.example.demo.entities.Participant;
import com.example.demo.repositories.ParticipantRepository;
import com.example.demo.enums.TeamCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User register(String name, String email, String password, Role role, Long participantId) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }
        
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        
        if (role == Role.PARTICIPANT) {
            Participant participant;
            if (participantId != null) {
                // If participantId is provided, use existing participant
                Optional<Participant> existingParticipant = participantRepository.findById(participantId);
                participant = existingParticipant.orElseThrow(() -> 
                    new RuntimeException("Participant with id " + participantId + " not found"));
            } else {
                // Create new participant automatically
                participant = createNewParticipant(name, email);
            }
            user.setParticipant(participant);
        }
        
        return userRepository.save(user);
    }

    private Participant createNewParticipant(String name, String email) {
        // Check if participant with this email already exists
        if (participantRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Participant with email " + email + " already exists");
        }
        
        Participant participant = new Participant();
        
        // Split the name into first and last name
        String[] nameParts = name.trim().split("\\s+", 2);
        if (nameParts.length >= 2) {
            participant.setFirstName(nameParts[0]);
            participant.setLastName(nameParts[1]);
        } else {
            // If only one name is provided, use it as first name
            participant.setFirstName(name);
            participant.setLastName("");
        }
        
        participant.setEmail(email);
        participant.setCategory(TeamCategoryEnum.AMATEUR); // Default category
        
        return participantRepository.save(participant);
    }

    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }
} 