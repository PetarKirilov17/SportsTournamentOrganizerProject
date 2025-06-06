package com.example.demo.services;

import com.example.demo.entities.Venue;
import com.example.demo.repositories.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class VenueService extends BasicService<Venue> {
    @Autowired
    private VenueRepository venueRepository;

    @Override
    protected CrudRepository<Venue, Long> getRepository() {
        return venueRepository;
    }
} 