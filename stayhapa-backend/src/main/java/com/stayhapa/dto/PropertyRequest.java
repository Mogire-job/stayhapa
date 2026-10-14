package com.stayhapa.dto;

import com.stayhapa.entity.Property.PropertyType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PropertyRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    private String city;
    private String state;
    private String country;
    private String zipCode;
    
    private Double latitude;
    private Double longitude;
    
    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "1.0", message = "Price per night must be at least KSh 1")
    private BigDecimal pricePerNight;
    
    @Min(value = 0, message = "Bedrooms cannot be negative")
    private Integer bedrooms;
    
    @Min(value = 1, message = "Beds must be at least 1")
    private Integer beds;
    
    @Min(value = 0, message = "Bathrooms cannot be negative")
    private Integer bathrooms;
    
    @Min(value = 1, message = "Max guests must be at least 1")
    private Integer maxGuests;

    private Boolean active;
    
    @NotNull(message = "Property type is required")
    private PropertyType propertyType;
    
    private List<String> amenities;
    
    private List<String> images;
}
