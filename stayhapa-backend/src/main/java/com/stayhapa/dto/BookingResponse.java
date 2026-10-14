package com.stayhapa.dto;

import com.stayhapa.entity.Booking;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class BookingResponse {
    private Long id;
    private Long propertyId;
    private String propertyTitle;
    private String propertyImage;
    private String city;
    private String country;
    private String address;
    private String hostName;
    private String guestName;
    private String guestEmail;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private long nights;
    private Integer numberOfGuests;
    private BigDecimal totalPrice;
    private Booking.BookingStatus status;
    private String specialRequests;
    private LocalDateTime createdAt;

    public static BookingResponse fromEntity(Booking booking, boolean includeGuestContact) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setPropertyId(booking.getProperty().getId());
        response.setPropertyTitle(booking.getProperty().getTitle());
        if (booking.getProperty().getImages() != null && !booking.getProperty().getImages().isEmpty()) {
            response.setPropertyImage(booking.getProperty().getImages().get(0));
        }
        response.setCity(booking.getProperty().getCity());
        response.setCountry(booking.getProperty().getCountry());
        if (booking.getStatus() == Booking.BookingStatus.CONFIRMED
                || booking.getStatus() == Booking.BookingStatus.COMPLETED) {
            response.setAddress(booking.getProperty().getAddress());
        }
        response.setHostName(booking.getProperty().getHost().getFullName());
        response.setGuestName(booking.getGuest().getFullName());
        if (includeGuestContact) {
            response.setGuestEmail(booking.getGuest().getEmail());
        }
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setNights(ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate()));
        response.setNumberOfGuests(booking.getNumberOfGuests());
        response.setTotalPrice(booking.getTotalPrice());
        response.setStatus(booking.getStatus());
        response.setSpecialRequests(booking.getSpecialRequests());
        response.setCreatedAt(booking.getCreatedAt());
        return response;
    }
}
