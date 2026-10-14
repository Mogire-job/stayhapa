package com.stayhapa.service;

import com.stayhapa.dto.PropertyRequest;
import com.stayhapa.dto.PropertyResponse;
import com.stayhapa.entity.Property;
import com.stayhapa.entity.User;
import com.stayhapa.repository.PropertyRepository;
import com.stayhapa.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class PropertyImagePersistenceIntegrationTest {
    private static final int IMAGE_DATA_LENGTH = 143_551;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void savesAndReadsBackBase64ImageDataLongerThanTheDefaultVarcharLimit() {
        User host = saveHost();
        String imageData = "data:image/jpeg;base64," + "A".repeat(IMAGE_DATA_LENGTH - 23);

        PropertyResponse response = propertyService.createProperty(
                propertyRequest(imageData),
                host.getEmail());

        propertyRepository.flush();
        entityManager.clear();

        Property savedProperty = propertyRepository.findById(response.getId()).orElseThrow();
        assertEquals(IMAGE_DATA_LENGTH, savedProperty.getImages().get(0).length());
        assertEquals(imageData, savedProperty.getImages().get(0));
    }

    private User saveHost() {
        User host = new User();
        host.setFirstName("Image");
        host.setLastName("Test Host");
        host.setEmail("image-host-" + UUID.randomUUID() + "@example.test");
        host.setPassword("not-used-in-service-tests");
        host.setRole(User.Role.HOST);
        host.setVerified(true);
        return userRepository.saveAndFlush(host);
    }

    private PropertyRequest propertyRequest(String imageData) {
        PropertyRequest request = new PropertyRequest();
        request.setTitle("Image Persistence Integration Stay");
        request.setDescription("A temporary listing for image persistence tests.");
        request.setAddress("Test Street, Nairobi");
        request.setCity("Nairobi");
        request.setCountry("Kenya");
        request.setPricePerNight(new BigDecimal("12500.00"));
        request.setBedrooms(2);
        request.setBeds(2);
        request.setBathrooms(1);
        request.setMaxGuests(4);
        request.setPropertyType(Property.PropertyType.APARTMENT);
        request.setAmenities(List.of("WiFi"));
        request.setImages(List.of(imageData));
        return request;
    }
}
