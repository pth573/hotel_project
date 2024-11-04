package com.project.hotel.service.impl;

import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.RoomImage;
import com.project.hotel.repository.RoomImageRepository;
import com.project.hotel.service.RoomImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomImageServiceImpl implements RoomImageService {

    @Autowired
    private RoomImageRepository roomImageRepository;

    @Override
    public void save(RoomImage roomImage) {
        roomImageRepository.save(roomImage);
    }

    @Override
    public List<RoomImage> findByRoomGroup(RoomGroup roomGroup) {
        return roomImageRepository.findRoomImageByRoomGroup(roomGroup);
    }

    @Override
    public void deleteByRoomGroup(RoomGroup roomGroup) {
        List<RoomImage> images = roomImageRepository.findRoomImageByRoomGroup(roomGroup);
            for (RoomImage image : images) {
            roomImageRepository.delete(image);
        }
    }
}
