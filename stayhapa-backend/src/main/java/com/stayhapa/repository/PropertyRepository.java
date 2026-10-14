package com.stayhapa.repository;

import com.stayhapa.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByHostId(Long hostId);
    List<Property> findByActiveTrue();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Property p WHERE p.id = :id AND p.active = true")
    Optional<Property> findActiveByIdForUpdate(@Param("id") Long id);
    
    @Query("SELECT p FROM Property p WHERE p.active = true AND " +
           "(:city IS NULL OR p.city = :city) AND " +
           "(:minPrice IS NULL OR p.pricePerNight >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.pricePerNight <= :maxPrice) AND " +
           "(:maxGuests IS NULL OR p.maxGuests >= :maxGuests)")
    List<Property> searchProperties(String city, java.math.BigDecimal minPrice, 
                                     java.math.BigDecimal maxPrice, Integer maxGuests);
}
