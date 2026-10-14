package com.stayhapa.dto;

import com.stayhapa.entity.Property;
import com.stayhapa.entity.Property.PropertyType;
import lombok.Data;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PropertyResponse {
    private Long id;
    private String title;
    private String description;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private Double latitude;
    private Double longitude;
    private Double mapLatitude;
    private Double mapLongitude;
    private BigDecimal pricePerNight;
    private Integer bedrooms;
    private Integer beds;
    private Integer bathrooms;
    private Integer maxGuests;
    private boolean active;
    private PropertyType propertyType;
    private String hostId;
    private String hostName;
    private List<String> amenities;
    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static PropertyResponse fromEntity(Property property) {
        PropertyResponse response = new PropertyResponse();
        response.setId(property.getId());
        response.setTitle(property.getTitle());
        response.setDescription(property.getDescription());
        response.setAddress(property.getAddress());
        response.setCity(property.getCity());
        response.setState(property.getState());
        response.setCountry(property.getCountry());
        response.setZipCode(property.getZipCode());
        response.setLatitude(property.getLatitude());
        response.setLongitude(property.getLongitude());
        response.setPricePerNight(property.getPricePerNight());
        response.setBedrooms(property.getBedrooms());
        response.setBeds(property.getBeds());
        response.setBathrooms(property.getBathrooms());
        response.setMaxGuests(property.getMaxGuests());
        response.setActive(property.isActive());
        response.setPropertyType(property.getPropertyType());
        response.setHostId(property.getHost() != null ? property.getHost().getUsername() : null);
        response.setHostName(property.getHost() != null ? property.getHost().getFullName() : null);
        response.setAmenities(property.getAmenities());
        response.setImages(property.getImages());
        response.setCreatedAt(property.getCreatedAt());
        response.setUpdatedAt(property.getUpdatedAt());
        return response;
    }

    public static PropertyResponse fromPublicEntity(Property property) {
        PropertyResponse response = fromEntity(property);
        response.setMapLatitude(approximateCoordinate(property.getLatitude()));
        response.setMapLongitude(approximateCoordinate(property.getLongitude()));
        response.setAddress(null);
        response.setZipCode(null);
        response.setLatitude(null);
        response.setLongitude(null);
        response.setHostId(null);
        return response;
    }

    private static Double approximateCoordinate(Double coordinate) {
        return coordinate == null
                ? null
                : BigDecimal.valueOf(coordinate).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
