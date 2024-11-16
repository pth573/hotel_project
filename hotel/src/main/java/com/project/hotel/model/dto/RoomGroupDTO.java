package com.project.hotel.model.dto;

import com.project.hotel.model.entity.Bed;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomImage;
import com.project.hotel.model.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RoomGroupDTO {

    private Long roomGroupId;
    private String groupName;
    private Float area;

    private Long pricePerNight;
    private Long comboPrice2H;
    private Long extraHourPrice;
    private String description;

    private int standardOccupancy;
    private int maxOccupancy;
    private int numChildrenFree;
    private Long extraAdult;
    private String imageUrl;
    private List<RoomDTO> rooms;
    private List<RoomImageDTO> images;
    private List<ServiceDTO> services;
    private List<BedDTO> beds;
    private Long availableRoomCount = 0L;
    private Long priceDateTime = 0L;
}
