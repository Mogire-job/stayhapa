package com.stayhapa.service;

import com.stayhapa.dto.PropertyRequest;
import com.stayhapa.dto.PropertyResponse;
import com.stayhapa.entity.Property;
import com.stayhapa.entity.User;
import com.stayhapa.repository.PropertyRepository;
import com.stayhapa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {
    
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public PropertyResponse createProperty(PropertyRequest request, String username) {
        User host = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        Property property = new Property();
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setAddress(request.getAddress());
        property.setCity(request.getCity());
        property.setState(request.getState());
        property.setCountry(request.getCountry());
        property.setZipCode(request.getZipCode());
        property.setLatitude(request.getLatitude());
        property.setLongitude(request.getLongitude());
        property.setPricePerNight(request.getPricePerNight());
        property.setBedrooms(request.getBedrooms());
        property.setBeds(request.getBeds());
        property.setBathrooms(request.getBathrooms());
        property.setMaxGuests(request.getMaxGuests());
        property.setPropertyType(request.getPropertyType());
        property.setActive(true);
        property.setHost(host);
        property.setAmenities(request.getAmenities());
        property.setImages(request.getImages());
        
        Property saved = propertyRepository.save(property);
        return PropertyResponse.fromEntity(saved);
    }
    
    public List<PropertyResponse> getAllActiveProperties() {
        return propertyRepository.findByActiveTrue()
                .stream()
                .map(PropertyResponse::fromPublicEntity)
                .collect(Collectors.toList());
    }
    
    public List<PropertyResponse> getPropertiesByHost(String username) {
        User host = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        return propertyRepository.findByHostId(host.getId())
                .stream()
                .map(PropertyResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    public PropertyResponse getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));
        if (!property.isActive()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
        }
        return PropertyResponse.fromPublicEntity(property);
    }
    
    public List<PropertyResponse> searchProperties(String city, BigDecimal minPrice, 
                                                   BigDecimal maxPrice, Integer maxGuests) {
        return propertyRepository.searchProperties(city, minPrice, maxPrice, maxGuests)
                .stream()
                .map(PropertyResponse::fromPublicEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PropertyResponse updateProperty(Long id, PropertyRequest request, String username) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));
        
        if (!property.getHost().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to update this property");
        }
        
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setAddress(request.getAddress());
        property.setCity(request.getCity());
        property.setState(request.getState());
        property.setCountry(request.getCountry());
        property.setZipCode(request.getZipCode());
        property.setLatitude(request.getLatitude());
        property.setLongitude(request.getLongitude());
        property.setPricePerNight(request.getPricePerNight());
        property.setBedrooms(request.getBedrooms());
        property.setBeds(request.getBeds());
        property.setBathrooms(request.getBathrooms());
        property.setMaxGuests(request.getMaxGuests());
        property.setPropertyType(request.getPropertyType());
        if (request.getActive() != null) {
            property.setActive(request.getActive());
        }
        property.setAmenities(request.getAmenities());
        property.setImages(request.getImages());
        
        Property updated = propertyRepository.save(property);
        return PropertyResponse.fromEntity(updated);
    }
    
    @Transactional
    public void deleteProperty(Long id, String username) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));
        
        if (!property.getHost().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to delete this property");
        }
        
        property.setActive(false);
        propertyRepository.save(property);
    }
}
