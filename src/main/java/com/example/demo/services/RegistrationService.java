package com.example.demo.services;

import com.example.demo.entities.Registration;
import com.example.demo.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService extends BasicService<Registration> {
    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    protected JpaRepository<Registration, Long> getRepository() {
        return registrationRepository;
    }
} 