package com.project.hotel.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookingDto {
    private int id;
    private String checkInDate;
    private String checkOutDate;
    private String checkInTime;
    private String checkOutTime;
    private int adults;
    private int children;
    private Long totalPrice;
    private Long amountHasPaid;
    private Date formattedCheckInDate;
    private Date formattedCheckOutDate;
    private Long roomId;
    private String email;

    @Override
    public String toString() {
        return "BookingDto{" +
                "id=" + id +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", checkInTime='" + checkInTime + '\'' +
                ", checkOutTime='" + checkOutTime + '\'' +
                ", adults=" + adults +
                ", children=" + children +
                ", totalPrice=" + totalPrice +
                ", amountHasPaid=" + amountHasPaid +
                ", formattedCheckInDate=" + formattedCheckInDate +
                ", formattedCheckOutDate=" + formattedCheckOutDate +
                ", roomId=" + roomId +
                ", email='" + email + '\'' +
                '}';
    }
}
