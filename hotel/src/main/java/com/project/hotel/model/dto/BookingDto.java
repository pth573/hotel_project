package com.project.hotel.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.hotel.model.enumType.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private CustomerDto2 customerDto;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

//    private Long roomId;
//    private String email;


    @JsonProperty("roomId")
    private Long roomId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("checkInDate")
    private String checkInDate;

    @JsonProperty("checkOutDate")
    private String checkOutDate;

    @JsonProperty("checkInTime")
    private String checkInTime;

    @JsonProperty("checkOutTime")
    private String checkOutTime;

    @JsonProperty("totalPrice")
    private Long totalPrice;

    @JsonProperty("amountHasPaid")
    private Long amountHasPaid;

    @JsonProperty("adults")
    private Integer adults;

    @JsonProperty("children")
    private Integer children;

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
