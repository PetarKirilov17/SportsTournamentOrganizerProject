package com.example.demo.repositories;

import com.example.demo.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    
    // Derived query methods
    Optional<Venue> findByName(String name);
    Optional<Venue> findByNameIgnoreCase(String name);
    List<Venue> findByNameContainingIgnoreCase(String name);
    List<Venue> findByAddressContainingIgnoreCase(String address);
    List<Venue> findByCapacityGreaterThanEqual(Integer capacity);
    List<Venue> findByCapacityLessThanEqual(Integer capacity);
    List<Venue> findByCapacityBetween(Integer minCapacity, Integer maxCapacity);
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
} 