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
public class RoomGroup {
    private int roomGroupId;
    private String groupName;
    private float area;
    private BedType bedType;
    private long pricePerNight;
    private long comboPrice2H;
    private long extraHourPrice;
    private String description;
    private int standardOccupancy;
    private String imageUrl;
}
