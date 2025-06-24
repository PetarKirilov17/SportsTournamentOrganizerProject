package com.example.demo.controllers;

import com.example.demo.entities.Venue;
import com.example.demo.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping
    public Iterable<Venue> getAllVenues() {
        return venueService.getAll();
    }

    @GetMapping("/{id}")
    public Venue getVenueById(@PathVariable Long id) {
        return venueService.getById(id);
    }

    @PostMapping
    public Venue createVenue(@RequestBody Venue venue) {
        return venueService.save(venue);
    }

    @PutMapping("/{id}")
    public Venue updateVenue(@PathVariable Long id, @RequestBody Venue venue) {
        venue.setId(id);
        return venueService.update(venue, id);
    }

    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id) {
        venueService.deleteById(id);
    }
} 