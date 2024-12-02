package com.project.hotel.service.impl;

import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.repository.RoomRepository;
import com.project.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

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

    public boolean isRoomAvailable(Long roomId, BookingDto bookingDto) {
        String checkInDateTime = bookingDto.getCheckInDate() + " " + bookingDto.getCheckInTime();
        String checkOutDateTime = bookingDto.getCheckOutDate() + " " + bookingDto.getCheckOutTime();
        Integer result = roomRepository.checkRoomAvailabilityById(roomId, checkInDateTime, checkOutDateTime);
        System.out.println("Check");
        System.out.println(roomId + " " + result);
        return result == 1;
//        return result != null && result == 1;  // Trả về true nếu phòng trống, false nếu phòng đã được đặt
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
    public Page<Room> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> findRoomAvailable(BookingDto bookingDto) {
        String checkInDateTime = bookingDto.getCheckInDate() + " " + bookingDto.getCheckInTime();
        String checkOutDateTime = bookingDto.getCheckOutDate() + " " + bookingDto.getCheckOutTime();
        return roomRepository.findRoomAvailable(checkInDateTime, checkOutDateTime);
    }

    @Override
    public List<Room> findRoomAvailableByGroup(BookingDto bookingDto, RoomGroup roomGroup) {
        return roomRepository.findAvailableRoomsByGroup(
                bookingDto.getCheckInDate(),
                bookingDto.getCheckOutDate(),
                roomGroup.getGroupName()
        );
    }
}

