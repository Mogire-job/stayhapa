package com.stayhapa.service;

import com.stayhapa.model.Booking;
import com.stayhapa.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByGuestId(Long guestId) {
        return bookingRepository.findByGuest_Id(guestId);
    }

    public List<Booking> getBookingsByListingId(Long listingId) {
        return bookingRepository.findByListing_Id(listingId);
    }

    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    booking.setGuest(bookingDetails.getGuest());
                    booking.setListing(bookingDetails.getListing());
                    booking.setCheckIn(bookingDetails.getCheckIn());
                    booking.setCheckOut(bookingDetails.getCheckOut());
                    booking.setTotalPrice(bookingDetails.getTotalPrice());
                    booking.setStatus(bookingDetails.getStatus());
                    booking.setUpdatedAt(java.time.LocalDateTime.now());
                    return bookingRepository.save(booking);
                })
                .orElse(null);
    }

    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
