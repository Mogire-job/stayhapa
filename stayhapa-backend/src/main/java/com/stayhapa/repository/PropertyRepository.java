package com.stayhapa.repository;

import com.stayhapa.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByHostId(Long hostId);
    List<Property> findByActiveTrue();
    
    @Query("SELECT p FROM Property p WHERE p.active = true AND " +
           "(:city IS NULL OR p.city = :city) AND " +
           "(:minPrice IS NULL OR p.pricePerNight >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.pricePerNight <= :maxPrice) AND " +
           "(:maxGuests IS NULL OR p.maxGuests >= :maxGuests)")
    List<Property> searchProperties(String city, java.math.BigDecimal minPrice, 
                                     java.math.BigDecimal maxPrice, Integer maxGuests);
}
