package com.project.hotel.model.entity;

import com.project.hotel.model.enumType.BedType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "room_group")
public class RoomGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomGroupId;
    private String groupName;
    private Float area;

    @Enumerated(EnumType.STRING)
    private BedType bedType;

    @OneToMany(mappedBy = "roomGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms;

    @OneToMany(mappedBy = "roomGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomImage> images;

    private Long pricePerNight;
    private Long comboPrice2H;
    private Long extraHourPrice;
    private String description;
    private Integer standardOccupancy;
    private String imageUrl;
}