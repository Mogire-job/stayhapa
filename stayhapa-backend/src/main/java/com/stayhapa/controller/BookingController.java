package com.stayhapa.controller;

import com.stayhapa.dto.BookingRequest;
import com.stayhapa.dto.BookingResponse;
import com.stayhapa.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/properties/{propertyId}")
    public ResponseEntity<BookingResponse> createBooking(
            @PathVariable Long propertyId,
            @Valid @RequestBody BookingRequest request,
            Authentication authentication) {
        BookingResponse booking = bookingService.createBooking(propertyId, request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping("/guest")
    public ResponseEntity<List<BookingResponse>> getGuestBookings(Authentication authentication) {
        return ResponseEntity.ok(bookingService.getGuestBookings(authentication.getName()));
    }

    @GetMapping("/host")
    public ResponseEntity<List<BookingResponse>> getHostBookings(Authentication authentication) {
        return ResponseEntity.ok(bookingService.getHostBookings(authentication.getName()));
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long bookingId,
            Authentication authentication) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId, authentication.getName()));
    }
}
