package com.stayhapa.controller;

import com.stayhapa.model.Listing;
import com.stayhapa.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/listings")
public class ListingController {
    private final ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping
    public List<Listing> getAllListings() {
        return listingService.getAllListings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        Optional<Listing> listing = listingService.getListingById(id);
        return listing.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/city/{city}")
    public List<Listing> getListingsByCity(@PathVariable String city) {
        return listingService.getListingsByCity(city);
    }

    @GetMapping("/country/{country}")
    public List<Listing> getListingsByCountry(@PathVariable String country) {
        return listingService.getListingsByCountry(country);
    }

    @GetMapping("/host/{hostId}")
    public List<Listing> getListingsByHostId(@PathVariable Long hostId) {
        return listingService.getListingsByHostId(hostId);
    }

    @PostMapping
    public Listing createListing(@RequestBody Listing listing) {
        return listingService.createListing(listing);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long id, @RequestBody Listing listingDetails) {
        Listing updatedListing = listingService.updateListing(id, listingDetails);
        if (updatedListing != null) {
            return ResponseEntity.ok(updatedListing);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        boolean deleted = listingService.deleteListing(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
