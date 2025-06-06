package com.example.demo.repositories;

import com.example.demo.entities.Registration;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<Registration, Long> {
} 