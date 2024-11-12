package com.project.hotel.service;

import com.project.hotel.model.entity.BookingRequest;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.Service;
import com.project.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface RoomService {
    void saveRoom(Room room);
    Room findById(Long id);
    List<Room> findAll();
    void deleteById(Long id);
    void updateRoom(Long roomId, Room updatedRoom);
    List<Room> findRoomAvailable(BookingRequest bookingRequest);
}
