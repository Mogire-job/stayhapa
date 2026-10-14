package com.stayhapa.service;

import com.stayhapa.dto.BookingRequest;
import com.stayhapa.dto.BookingResponse;
import com.stayhapa.entity.Booking;
import com.stayhapa.entity.Booking.BookingStatus;
import com.stayhapa.entity.Property;
import com.stayhapa.entity.User;
import com.stayhapa.repository.BookingRepository;
import com.stayhapa.repository.PropertyRepository;
import com.stayhapa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookingResponse createBooking(Long propertyId, BookingRequest request, String username) {
        User guest = findUser(username);
        requireRole(guest, User.Role.GUEST);

        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check-out must be after check-in");
        }
        if (request.getCheckInDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check-in cannot be in the past");
        }

        Property property = propertyRepository.findActiveByIdForUpdate(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stay is not available"));
        if (property.getHost().getId().equals(guest.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot reserve your own stay");
        }
        if (property.getMaxGuests() != null && request.getNumberOfGuests() > property.getMaxGuests()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Guest count exceeds this stay's capacity");
        }

        boolean datesUnavailable = bookingRepository
                .existsByPropertyIdAndStatusAndCheckInDateLessThanAndCheckOutDateGreaterThan(
                        propertyId,
                        BookingStatus.CONFIRMED,
                        request.getCheckOutDate(),
                        request.getCheckInDate());
        if (datesUnavailable) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Those dates are no longer available");
        }

        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        Booking booking = new Booking();
        booking.setProperty(property);
        booking.setGuest(guest);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setSpecialRequests(request.getSpecialRequests());
        booking.setTotalPrice(property.getPricePerNight().multiply(BigDecimal.valueOf(nights)));
        booking.setStatus(BookingStatus.CONFIRMED);

        return BookingResponse.fromEntity(bookingRepository.save(booking), false);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getGuestBookings(String username) {
        User guest = findUser(username);
        requireRole(guest, User.Role.GUEST);
        return bookingRepository.findByGuestIdOrderByCreatedAtDesc(guest.getId())
                .stream()
                .map(booking -> BookingResponse.fromEntity(booking, false))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getHostBookings(String username) {
        User host = findUser(username);
        requireRole(host, User.Role.HOST);
        return bookingRepository.findByPropertyHostIdOrderByCreatedAtDesc(host.getId())
                .stream()
                .map(booking -> BookingResponse.fromEntity(booking, true))
                .toList();
    }

    @Transactional
    public BookingResponse cancelBooking(Long bookingId, String username) {
        User requester = findUser(username);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));

        boolean isGuest = requester.getRole() == User.Role.GUEST
                && booking.getGuest().getId().equals(requester.getId());
        boolean isHost = requester.getRole() == User.Role.HOST
                && booking.getProperty().getHost().getId().equals(requester.getId());
        if (!isGuest && !isHost) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot cancel this reservation");
        }
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Only confirmed reservations can be cancelled");
        }
        if (!LocalDate.now().isBefore(booking.getCheckInDate())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Reservations cannot be cancelled on or after check-in");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return BookingResponse.fromEntity(bookingRepository.save(booking), isHost);
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    private void requireRole(User user, User.Role role) {
        if (user.getRole() != role) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This action is not allowed for your account type");
        }
    }
}
