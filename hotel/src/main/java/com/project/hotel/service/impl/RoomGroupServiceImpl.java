package com.project.hotel.service.impl;
import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.repository.RoomGroupRepository;
import com.project.hotel.repository.RoomImageRepository;
import com.project.hotel.service.RoomGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoomGroupServiceImpl implements RoomGroupService {

    private final RoomGroupRepository roomGroupRepository;
    private final RoomImageRepository roomImageRepository;

    @Override
    public void save(RoomGroup roomGroup) {
        roomGroupRepository.save(roomGroup);
    }

    @Override
    public RoomGroup findById(Long id) {
        Optional<RoomGroup> result = roomGroupRepository.findById(id);
        RoomGroup roomGroup = null;
        if(result.isPresent()){
            roomGroup = result.get();
        }
        else{
            throw new RuntimeException("Không thấy RoomGroup có id: " + id);
        }
        return roomGroup;
    }


    @Override
    public List<RoomGroup> findAll() {
        return roomGroupRepository.findAll();
    }


    @Override
    public void deleteById(Long id) {
        roomGroupRepository.deleteById(id);
    }



    public void updateRoomGroup(RoomGroup roomGroup) {
        if (roomGroupRepository.existsById(roomGroup.getRoomGroupId())) {
            roomGroupRepository.save(roomGroup);
        } else {
            throw new RuntimeException("Nhóm phòng không tồn tại");
        }
    }


    @Override
    public Long calculatePrice(BookingDto bookingDto, RoomGroup roomGroup) {
        Long pricePerNight = roomGroup.getPricePerNight();
        Long comboPrice2H = roomGroup.getComboPrice2H();
        Long extraHourPrice = roomGroup.getExtraHourPrice();
        Long extraAdult = roomGroup.getExtraAdult();
        int numChildrenFree = roomGroup.getNumChildrenFree();
        int maxOccupanccy = roomGroup.getMaxOccupancy();
        int standard = roomGroup.getStandardOccupancy();

        String checkInDateTime = bookingDto.getCheckInDate() + " " + bookingDto.getCheckInTime() + ":00";
        String checkOutDateTime = bookingDto.getCheckOutDate() + " " + bookingDto.getCheckOutTime() + ":00";
        int adultsForm = bookingDto.getAdults();
        int childrenFrom = bookingDto.getChildren();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(checkInDateTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(checkOutDateTime, formatter);

//        LocalDateTime checkInStartOfDay = LocalDateTime.of(startDateTime.toLocalDate(), LocalTime.of(14, 0));
//        LocalDateTime checkOutEndOfDay = LocalDateTime.of(endDateTime.toLocalDate(), LocalTime.of(11, 0));

//        while (checkInStartOfDay.isBefore(startDateTime)) {
//            checkInStartOfDay.plusDays(1);
//        }
//        while (checkOutEndOfDay.isBefore(endDateTime)) {
//            checkOutEndOfDay.plusDays(1);
//        }
//        checkOutEndOfDay.minusDays(1);

        long daysBetween = ChronoUnit.DAYS.between(startDateTime, endDateTime);
        if(daysBetween < 1) daysBetween = 1;
        return daysBetween * roomGroup.getPricePerNight();
    }

    @Override
    public RoomGroup findByGroupName(String name) {
        Optional<RoomGroup> result = Optional.ofNullable(roomGroupRepository.findByGroupName(name));
        RoomGroup roomGroup = null;
        if(result.isPresent()){
            roomGroup = result.get();
        }
        else{
            return null;
        }
        return roomGroup;
    }
}
