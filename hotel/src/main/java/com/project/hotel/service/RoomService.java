package com.project.hotel.service;

import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    void saveRoom(Room room);
    Room findById(Long id);
    List<Room> findAll();
    Page<Room> findAll(Pageable pageable);
    boolean isRoomAvailable(Long roomId, BookingDto bookingDto);
    void deleteById(Long id);
    void updateRoom(Long roomId, Room updatedRoom);
    List<Room> findRoomAvailable(BookingDto bookingDto);
    List<Room> findRoomAvailableByGroup(BookingDto bookingDto, RoomGroup roomGroup);
}
