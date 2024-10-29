package com.project.hotel.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    private int bookingId;
    private String checkInDate;
    private String checkOutDate;
    private long totalPrice;
    private BookingStatus status;
}
