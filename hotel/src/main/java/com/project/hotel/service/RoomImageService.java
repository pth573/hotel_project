package com.project.hotel.service;

import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.RoomImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomImageService {
    void save(RoomImage roomImage);
    List<RoomImage> findByRoomGroup(RoomGroup roomGroup);
    void deleteByRoomGroup(RoomGroup roomGroup);
    void deleteById(Long id);
}
