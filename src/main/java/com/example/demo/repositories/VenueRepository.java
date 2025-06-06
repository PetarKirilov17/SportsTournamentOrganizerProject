package com.example.demo.repositories;

import com.example.demo.entities.Venue;
import org.springframework.data.repository.CrudRepository;

public interface VenueRepository extends CrudRepository<Venue, Long> {
} 