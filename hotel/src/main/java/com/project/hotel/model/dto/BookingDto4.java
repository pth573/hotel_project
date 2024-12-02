package com.project.hotel.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookingDto4 {
    private int id;
    //    private String checkInDate;
//    private String checkOutDate;
//    private String checkInTime;
//    private String checkOutTime;
//    private int adults;
//    private int children;
//    private Long totalPrice;
//    private Long amountHasPaid;
    private Date formattedCheckInDate;
    private Date formattedCheckOutDate;
//    private Long roomId;
//    private String email;
}
