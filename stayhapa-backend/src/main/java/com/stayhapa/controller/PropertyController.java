package com.stayhapa.controller;

import com.stayhapa.dto.PropertyRequest;
import com.stayhapa.dto.PropertyResponse;
import com.stayhapa.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PropertyController {
    
    private final PropertyService propertyService;
    
    @PostMapping
    public ResponseEntity<PropertyResponse> createProperty(
            @Valid @RequestBody PropertyRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        PropertyResponse property = propertyService.createProperty(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(property);
    }
    
    @GetMapping
    public ResponseEntity<List<PropertyResponse>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllActiveProperties());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }
    
    @GetMapping("/my-properties")
    public ResponseEntity<List<PropertyResponse>> getMyProperties(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(propertyService.getPropertiesByHost(username));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<PropertyResponse>> searchProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer maxGuests) {
        return ResponseEntity.ok(propertyService.searchProperties(city, minPrice, maxPrice, maxGuests));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponse> updateProperty(
            @PathVariable Long id,
            @Valid @RequestBody PropertyRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(propertyService.updateProperty(id, request, username));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(
            @PathVariable Long id,
            Authentication authentication) {
        String username = authentication.getName();
        propertyService.deleteProperty(id, username);
        return ResponseEntity.noContent().build();
    }
}
