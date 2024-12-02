package com.project.hotel.model.dto;

import com.project.hotel.model.enumType.RoomStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomDTO2 {
    private Long roomId;
    private String roomName;
    private String description;
    private Long totalPrice;
    private RoomGroupDTO roomGroup;
    private RoomStatus roomStatus;
    List<BookingDto> bookingDtos;
}
