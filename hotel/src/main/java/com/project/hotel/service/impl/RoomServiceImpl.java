package com.project.hotel.service.impl;

import com.project.hotel.model.entity.BookingRequest;
import com.project.hotel.model.entity.Room;
import com.project.hotel.repository.RoomRepository;
import com.project.hotel.repository.ServiceRepository;
import com.project.hotel.service.RoomService;
import com.project.hotel.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public void updateRoom(Long roomId, Room updatedRoom) {
        Room room = findById(roomId);
        if (room != null) {
            room.setRoomName(updatedRoom.getRoomName());
            room.setDescription(updatedRoom.getDescription());
            room.setServices(updatedRoom.getServices());
            room.setRoomGroup(updatedRoom.getRoomGroup());
            roomRepository.save(room);
        }
    }

    @Override
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room findById(Long id) {
        Optional<Room> result = roomRepository.findById(id);
        Room room = null;
        if(result.isPresent()){
            room = result.get();
        }
        else{
            throw new RuntimeException("Không thấy phòng có id: " + id);
        }
        return room;
    }


    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> findRoomAvailable(BookingRequest bookingRequest) {
        String checkInDateTime = bookingRequest.getCheckInDate() + " " + bookingRequest.getCheckInTime();
        String checkOutDateTime = bookingRequest.getCheckOutDate() + " " + bookingRequest.getCheckOutTime();

        return roomRepository.findRoomAvailable(checkInDateTime, checkOutDateTime);
    }
}

