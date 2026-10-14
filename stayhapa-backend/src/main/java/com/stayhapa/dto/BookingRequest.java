package com.stayhapa.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in cannot be in the past")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    @NotNull(message = "Guest count is required")
    @Min(value = 1, message = "At least one guest is required")
    private Integer numberOfGuests;

    @Size(max = 1000, message = "Special requests cannot exceed 1000 characters")
    private String specialRequests;
}
