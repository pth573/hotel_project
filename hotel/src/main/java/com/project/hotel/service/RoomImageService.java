package com.project.hotel.service;

import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.RoomImage;

import java.util.List;

public interface RoomImageService {
    void save(RoomImage roomImage);
    List<RoomImage> findByRoomGroup(RoomGroup roomGroup);
    void deleteByRoomGroup(RoomGroup roomGroup);
    void deleteById(Long id);
}
