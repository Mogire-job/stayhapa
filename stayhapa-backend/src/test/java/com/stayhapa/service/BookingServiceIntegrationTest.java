package com.stayhapa.service;

import com.stayhapa.dto.BookingRequest;
import com.stayhapa.dto.BookingResponse;
import com.stayhapa.entity.Booking;
import com.stayhapa.entity.Property;
import com.stayhapa.entity.User;
import com.stayhapa.repository.BookingRepository;
import com.stayhapa.repository.PropertyRepository;
import com.stayhapa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class BookingServiceIntegrationTest {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void confirmsAvailableDatesAndPersistsTheServerCalculatedTotal() {
        User host = saveUser("host");
        User guest = saveUser("guest");
        Property property = saveProperty(host, 3, "12000.00");
        LocalDate checkIn = LocalDate.now().plusDays(10);

        BookingResponse response = bookingService.createBooking(
                property.getId(),
                request(checkIn, checkIn.plusDays(2), 2),
                guest.getEmail());

        assertEquals(Booking.BookingStatus.CONFIRMED, response.getStatus());
        assertEquals(new BigDecimal("24000.00"), response.getTotalPrice());
        assertEquals("Test Street, Nairobi", response.getAddress());
        assertEquals(1, bookingRepository.findByGuestIdOrderByCreatedAtDesc(guest.getId()).size());
        assertEquals(1, bookingRepository.findByPropertyHostIdOrderByCreatedAtDesc(host.getId()).size());
    }

    @Test
    void rejectsOverlappingConfirmedReservations() {
        User host = saveUser("host");
        User firstGuest = saveUser("guest");
        User secondGuest = saveUser("other-guest");
        Property property = saveProperty(host, 4, "10000.00");
        LocalDate checkIn = LocalDate.now().plusDays(20);

        Booking occupied = new Booking();
        occupied.setProperty(property);
        occupied.setGuest(firstGuest);
        occupied.setCheckInDate(checkIn);
        occupied.setCheckOutDate(checkIn.plusDays(3));
        occupied.setNumberOfGuests(2);
        occupied.setTotalPrice(new BigDecimal("30000.00"));
        occupied.setStatus(Booking.BookingStatus.CONFIRMED);
        bookingRepository.saveAndFlush(occupied);

        ResponseStatusException error = assertThrows(
                ResponseStatusException.class,
                () -> bookingService.createBooking(
                        property.getId(),
                        request(checkIn.plusDays(2), checkIn.plusDays(4), 2),
                        secondGuest.getEmail()));

        assertEquals(HttpStatus.CONFLICT, error.getStatusCode());
    }

    @Test
    void rejectsGuestCountsAboveTheListingCapacity() {
        User host = saveUser("host");
        User guest = saveUser("guest");
        Property property = saveProperty(host, 2, "9000.00");
        LocalDate checkIn = LocalDate.now().plusDays(30);

        ResponseStatusException error = assertThrows(
                ResponseStatusException.class,
                () -> bookingService.createBooking(
                        property.getId(),
                        request(checkIn, checkIn.plusDays(1), 3),
                        guest.getEmail()));

        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
        assertTrue(bookingRepository.findAll().isEmpty());
    }

    private User saveUser(String suffix) {
        String unique = suffix + "-" + java.util.UUID.randomUUID();
        User user = new User();
        user.setFirstName("Test");
        user.setLastName(suffix);
        user.setEmail(unique + "@example.test");
        user.setPassword("not-used-in-service-tests");
        user.setRole(suffix.equals("host") ? User.Role.HOST : User.Role.GUEST);
        user.setVerified(true);
        return userRepository.saveAndFlush(user);
    }

    private Property saveProperty(User host, int maxGuests, String price) {
        Property property = new Property();
        property.setTitle("Integration Test Stay");
        property.setDescription("A temporary listing for booking tests.");
        property.setAddress("Test Street, Nairobi");
        property.setCity("Nairobi");
        property.setCountry("Kenya");
        property.setPricePerNight(new BigDecimal(price));
        property.setBedrooms(2);
        property.setBeds(2);
        property.setBathrooms(1);
        property.setMaxGuests(maxGuests);
        property.setActive(true);
        property.setPropertyType(Property.PropertyType.APARTMENT);
        property.setHost(host);
        property.setAmenities(List.of("WiFi"));
        property.setImages(List.of());
        return propertyRepository.saveAndFlush(property);
    }

    private BookingRequest request(LocalDate checkIn, LocalDate checkOut, int guests) {
        BookingRequest request = new BookingRequest();
        request.setCheckInDate(checkIn);
        request.setCheckOutDate(checkOut);
        request.setNumberOfGuests(guests);
        return request;
    }
}
