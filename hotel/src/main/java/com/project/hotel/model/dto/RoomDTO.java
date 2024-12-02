package com.project.hotel.model.dto;

import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.enumType.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RoomDTO {
    private Long roomId;
    private String roomName;
    private String description;
    private Long totalPrice;
    private RoomGroupDTO roomGroup;
    private RoomStatus roomStatus;
}
