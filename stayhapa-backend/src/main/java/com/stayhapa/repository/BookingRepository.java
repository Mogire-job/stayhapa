package com.stayhapa.repository;

import com.stayhapa.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByGuestId(Long guestId);
    List<Booking> findByPropertyHostId(Long hostId);
    List<Booking> findByPropertyId(Long propertyId);
}
