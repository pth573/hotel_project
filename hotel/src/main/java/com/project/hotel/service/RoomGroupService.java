package com.project.hotel.service;


import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RoomGroupService {
    void save(RoomGroup roomGroup);
    RoomGroup findById(Long id);
    List<RoomGroup> findAll();
    void deleteById(Long id);
    void updateRoomGroup(RoomGroup roomGroup);
}
