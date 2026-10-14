package com.stayhapa.repository;

import com.stayhapa.entity.Booking;
import com.stayhapa.entity.Booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByGuestIdOrderByCreatedAtDesc(Long guestId);
    List<Booking> findByPropertyHostIdOrderByCreatedAtDesc(Long hostId);
    boolean existsByPropertyIdAndStatusAndCheckInDateLessThanAndCheckOutDateGreaterThan(
            Long propertyId,
            BookingStatus status,
            java.time.LocalDate requestedCheckOut,
            java.time.LocalDate requestedCheckIn);
}
