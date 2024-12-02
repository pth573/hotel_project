package com.project.hotel.service.impl;
import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.repository.RoomGroupRepository;
import com.project.hotel.repository.RoomImageRepository;
import com.project.hotel.service.RoomGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
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


    public Long calculatePrice(BookingDto bookingDto, RoomGroup roomGroup) {
        Long pricePerNight = roomGroup.getPricePerNight();
        Long comboPrice2H = roomGroup.getComboPrice2H();
        Long extraHourPrice = roomGroup.getExtraHourPrice();
        Long extraAdult = roomGroup.getExtraAdult();
        int numChildrenFree = roomGroup.getNumChildrenFree();
        int maxOccupanccy = roomGroup.getMaxOccupancy();
        int standard = roomGroup.getStandardOccupancy();

        long numChildPrice = 0;
        if(bookingDto.getChildren() > numChildrenFree){
            numChildrenFree = bookingDto.getChildren() - numChildrenFree;
        }

        long numAdultPrice = 0;
        if(bookingDto.getAdults() > maxOccupanccy){
            numAdultPrice = bookingDto.getAdults() - maxOccupanccy;
        }



        // Dữ liệu từ booking
        String checkInDateTime = bookingDto.getCheckInDate() + " " + bookingDto.getCheckInTime() + ":00";
        String checkOutDateTime = bookingDto.getCheckOutDate() + " " + bookingDto.getCheckOutTime() + ":00";
        System.out.println("0: " + checkInDateTime + " " + checkOutDateTime);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(checkInDateTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(checkOutDateTime, formatter);

        // Xác định mốc 14:00 cho ngày check-in và 11:00 cho ngày check-out
        LocalDateTime checkInStartOfDay = LocalDateTime.of(startDateTime.toLocalDate(), LocalTime.of(14, 0));
        LocalDateTime checkOutEndOfDay = LocalDateTime.of(endDateTime.toLocalDate(), LocalTime.of(11, 0));

//        // Điều chỉnh nếu thời gian thực tế không nằm trong khoảng chuẩn
//        if (startDateTime.isBefore(checkInStartOfDay)) {
//            checkInStartOfDay = checkInStartOfDay.minusDays(1);
////            long hoursBetween = ChronoUnit.HOURS.between(startDateTime, checkInStartOfDay);
//
//        }
//        if (endDateTime.isAfter(checkOutEndOfDay)) {
//            checkOutEndOfDay = checkOutEndOfDay.plusDays(1);
//        }
//
//        // Tính số ngày
//        long daysBetween = ChronoUnit.DAYS.between(checkInStartOfDay, checkOutEndOfDay) + 1;
//
//        // Đảm bảo tối thiểu 1 ngày
////        if (daysBetween < 1) daysBetween = 1;
//
//        // Tính giá
//        return daysBetween * pricePerNight;



        // Thời gian tiêu chuẩn
        LocalDateTime standardCheckIn = startDateTime.withHour(14).withMinute(0);
        LocalDateTime standardCheckOut = endDateTime.withHour(11).withMinute(0);

        // Điều chỉnh thời gian bắt đầu ngày check-in
        if (startDateTime.isBefore(standardCheckIn)) {
            standardCheckIn = standardCheckIn.minusDays(1);
        }

        // Tính số ngày tiêu chuẩn
        long totalDays = ChronoUnit.DAYS.between(standardCheckIn, standardCheckOut) + 1;
//        if(totalDays )

        LocalDateTime tmpCheckIn = standardCheckIn.plusDays(1);

        // Phụ thu nếu check-in sớm
//        long earlyCheckInHours = ChronoUnit.HOURS.between(startDateTime, standardCheckIn);
//        if (earlyCheckInHours < 0) earlyCheckInHours = 0; // Không tính nếu check-in đúng giờ
//

        long earlyCheckInHours = ChronoUnit.HOURS.between(startDateTime, tmpCheckIn);
        if (earlyCheckInHours < 0) earlyCheckInHours = 0; // Không tính nếu check-in đúng giờ



        // Phụ thu nếu check-out muộn
        long lateCheckOutHours = ChronoUnit.HOURS.between(standardCheckOut, endDateTime);
        if (lateCheckOutHours < 0) lateCheckOutHours = 0; // Không tính nếu check-out đúng giờ

        System.out.println("1: " + startDateTime + " " + tmpCheckIn);
        System.out.println("2: " + endDateTime + " " + standardCheckOut);
        System.out.println("3: " + earlyCheckInHours + " " + lateCheckOutHours);

        // Tính tổng giá
        Long totalPrice = (Long) (totalDays * pricePerNight + (earlyCheckInHours + lateCheckOutHours) * extraHourPrice + (numChildPrice + numAdultPrice) * extraAdult);


        long comboTime = ChronoUnit.HOURS.between(startDateTime, endDateTime);
        Long priceTime = 0L;
        if(comboTime >= 2){
            priceTime = comboPrice2H + extraHourPrice * (comboTime - 2);
        }
        else if(comboTime == 1){
            priceTime = comboPrice2H;
        }

        return  Math.min(totalPrice, priceTime);
    }


//    @Override
//    public Long calculatePrice(BookingDto bookingDto, RoomGroup roomGroup) {
//        Long pricePerNight = roomGroup.getPricePerNight();
//        Long comboPrice2H = roomGroup.getComboPrice2H();
//        Long extraHourPrice = roomGroup.getExtraHourPrice();
//        Long extraAdult = roomGroup.getExtraAdult();
//        int numChildrenFree = roomGroup.getNumChildrenFree();
//        int maxOccupanccy = roomGroup.getMaxOccupancy();
//        int standard = roomGroup.getStandardOccupancy();
//
//        String checkInDateTime = bookingDto.getCheckInDate() + " " + bookingDto.getCheckInTime() + ":00";
//        String checkOutDateTime = bookingDto.getCheckOutDate() + " " + bookingDto.getCheckOutTime() + ":00";
//        int adultsForm = bookingDto.getAdults();
//        int childrenFrom = bookingDto.getChildren();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime startDateTime = LocalDateTime.parse(checkInDateTime, formatter);
//        LocalDateTime endDateTime = LocalDateTime.parse(checkOutDateTime, formatter);
//
////        LocalDateTime checkInStartOfDay = LocalDateTime.of(startDateTime.toLocalDate(), LocalTime.of(14, 0));
////        LocalDateTime checkOutEndOfDay = LocalDateTime.of(endDateTime.toLocalDate(), LocalTime.of(11, 0));
//
////        while (checkInStartOfDay.isBefore(startDateTime)) {
////            checkInStartOfDay.plusDays(1);
////        }
////        while (checkOutEndOfDay.isBefore(endDateTime)) {
////            checkOutEndOfDay.plusDays(1);
////        }
////        checkOutEndOfDay.minusDays(1);
//
//
//
//        long daysBetween = ChronoUnit.DAYS.between(startDateTime, endDateTime);
//        if(daysBetween < 1) daysBetween = 1;
//        return daysBetween * roomGroup.getPricePerNight();
//    }

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
