package com.project.hotel.service.impl;
import com.project.hotel.model.entity.BookingRequest;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.repository.RoomGroupRepository;
import com.project.hotel.repository.RoomImageRepository;
import com.project.hotel.service.RoomGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RoomGroupServiceImpl implements RoomGroupService {

    @Autowired
    private RoomGroupRepository roomGroupRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

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

//    @Override
//    public void deleteById(Long id) {
//        Optional<RoomGroup> roomGroupOptional = roomGroupRepository.findById(id);
//        if (roomGroupOptional.isPresent()) {
//            RoomGroup roomGroup = roomGroupOptional.get();
//            roomImageRepository.deleteRoomImageBy(roomGroup);
//
//            roomGroupRepository.delete(roomGroup);
//        } else {
//            throw new ResourceNotFoundException("RoomGroup not found with id " + id);
//        }
//    }

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
    public Long calculatePrice(BookingRequest bookingRequest, RoomGroup roomGroup) {
        Long pricePerNight = roomGroup.getPricePerNight();
        Long comboPrice2H = roomGroup.getComboPrice2H();
        Long extraHourPrice = roomGroup.getExtraHourPrice();
        Long extraAdult = roomGroup.getExtraAdult();
        int numChildrenFree = roomGroup.getNumChildrenFree();
        int maxOccupanccy = roomGroup.getMaxOccupancy();
        int standard = roomGroup.getStandardOccupancy();

        String checkInDateTime = bookingRequest.getCheckInDate() + " " + bookingRequest.getCheckInTime() + ":00";
        String checkOutDateTime = bookingRequest.getCheckOutDate() + " " + bookingRequest.getCheckOutTime() + ":00";
        int adultsForm = bookingRequest.getAdults();
        int childrenFrom = bookingRequest.getChildren();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(checkInDateTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(checkOutDateTime, formatter);

        // Xác định thời gian chuẩn (14h cho check-in, 11h cho check-out)
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
        return daysBetween * roomGroup.getPricePerNight();
//        int days = ce
    }




//    @Override
//    public Long calculatePrice(BookingRequest bookingRequest, RoomGroup roomGroup) {
//        Long pricePerNight = roomGroup.getPricePerNight();
//        Long comboPrice2H = roomGroup.getComboPrice2H();
//        Long extraHourPrice = roomGroup.getExtraHourPrice();
//        Long extraAdult = roomGroup.getExtraAdult();
//        int numChildrenFree = roomGroup.getNumChildrenFree();
//        int maxOccupanccy = roomGroup.getMaxOccupancy();
//        int standard = roomGroup.getStandardOccupancy();
//
//        String checkInDateTime = bookingRequest.getCheckInDate() + " " + bookingRequest.getCheckInTime() + ":00";
//        String checkOutDateTime = bookingRequest.getCheckOutDate() + " " + bookingRequest.getCheckOutTime() + ":00";
//        int adultsForm = bookingRequest.getAdults();
//        int childrenFrom = bookingRequest.getChildren();
//
//        System.out.println("11111 " + checkInDateTime);
//        System.out.println("2222: " + checkOutDateTime);
//
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime startDateTime = LocalDateTime.parse(checkInDateTime, formatter);
//        LocalDateTime endDateTime = LocalDateTime.parse(checkOutDateTime, formatter);
//
//        // Xác định thời gian chuẩn (14h cho check-in, 11h cho check-out)
//        LocalDateTime checkInStartOfDay = LocalDateTime.of(startDateTime.toLocalDate(), LocalTime.of(14, 0));
//        LocalDateTime checkOutEndOfDay = LocalDateTime.of(endDateTime.toLocalDate(), LocalTime.of(11, 0));
//
//        long daysBetween = ChronoUnit.DAYS.between(checkInStartOfDay, checkOutEndOfDay);
//
//        long totalPrice = 0;
//
//        // Xử lý trường hợp check-in trước 14:00
//        if (startDateTime.isBefore(checkInStartOfDay)) {
//            // Tính phí extra giờ cho check-in sớm
//            long earlyCheckInHours = ChronoUnit.HOURS.between(startDateTime, checkInStartOfDay);
//            totalPrice += extraHourPrice * earlyCheckInHours;
//
//            // Kiểm tra nếu phí extra giờ cao hơn giá ngày, tính theo giá ngày
//            if (extraHourPrice * earlyCheckInHours > pricePerNight) {
//                totalPrice = pricePerNight; // Nếu giá thêm giờ cao hơn giá một ngày, tính theo giá ngày
//            }
//        }
//
//        // Xử lý trường hợp check-out sau 11:00
//        if (endDateTime.isAfter(checkOutEndOfDay)) {
//            // Tính phí extra giờ cho check-out muộn
//            long lateCheckOutHours = ChronoUnit.HOURS.between(checkOutEndOfDay, endDateTime);
//            totalPrice += extraHourPrice * lateCheckOutHours;
//
//            // Kiểm tra nếu phí extra giờ cao hơn giá ngày, tính theo giá ngày
//            if (extraHourPrice * lateCheckOutHours > pricePerNight) {
//                totalPrice = pricePerNight; // Nếu giá thêm giờ cao hơn giá một ngày, tính theo giá ngày
//            }
//        }
//
//        // Tính giá theo đêm nếu không có trường hợp check-in trước 14h hoặc check-out sau 11h
//        if (daysBetween > 0) {
//            totalPrice += pricePerNight * daysBetween;
//        }
//
//        // Kiểm tra nếu có combo giá cho 2 giờ đầu tiên
//        long bookingHours = ChronoUnit.HOURS.between(startDateTime, endDateTime);
//        if (bookingHours <= 2) {
//            totalPrice = Math.min(totalPrice, comboPrice2H); // Nếu booking < 2h, lấy combo giá
//        } else {
//            totalPrice = Math.min(totalPrice, pricePerNight * daysBetween); // Nếu booking > 2h, tính theo ngày
//        }
//
//        if(adultsForm > standard){
//            totalPrice += (adultsForm - standard) * extraAdult;
//        }
//        if(childrenFrom > numChildrenFree){
//            totalPrice += (childrenFrom - numChildrenFree) * extraAdult;
//        }
//
//        System.out.println("GR: " + roomGroup.getGroupName());
//        System.out.println("Total price: " + totalPrice);
//        return totalPrice;
//    }
//


    // @Override
    // public void calculatePrice(String checkInDateTime, String checkOutDateTime, RoomGroup roomGroup) {
    //     Long pricePerNight = roomGroup.getPricePerNight();
    //     Long comboPrice2H = roomGroup.getComboPrice2H();
    //     Long extraHourPrice = roomGroup.getExtraHourPrice();
    //     Long extraAdult = roomGroup.getExtraAdult();
    //     String checkIn[] = checkInDateTime.split(" ");
    //     String dateCheckIn = checkIn[0];
    //     String timeCheckIn = checkIn[1];

    //     String checkOut[] = checkOutDateTime.split(" ");
    //     String dateCheckOut = checkOut[0];
    //     String timeCheckOut = checkOut[1];

    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //     LocalDateTime startDateTime = LocalDateTime.parse(checkInDateTime, formatter);
    //     LocalDateTime endDateTime = LocalDateTime.parse(checkOutDateTime, formatter);


    //     int ans = 0;

    //     // Định dạng chuỗi ngày
    //     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    //     LocalDate date = LocalDate.parse(dateCheckIn, dateFormatter);
    //     LocalTime time = LocalTime.of(14, 0);
    //     LocalDateTime dateTime14 = LocalDateTime.of(date, time);

    //     LocalDate date2 = LocalDate.parse(dateCheckOut, dateFormatter);
    //     LocalTime time2 = LocalTime.of(11, 0);
    //     LocalDateTime dateTime11 = LocalDateTime.of(date2, time2);

    //     long totalPriceDay = 0;
    //     long hours = ChronoUnit.HOURS.between(startDateTime, endDateTime);

    //     int days = 0;
    //     while (dateTime14.isBefore(startDateTime)) {
    //         dateTime14 = dateTime14.plusDays(1);
    //     }

    //     while (dateTime11.isBefore(endDateTime)) {
    //         dateTime11 = dateTime11.plusDays(1);
    //         days++;
    //     }
    //     days--;
    //     hours = hours - 21 * days;
    //     ans += pricePerNight * days;
    //     if(days > 0){
            
    //     }

    //     if(hours > 0){
    //         Long tmp =  Math.min(comboPrice2H, extraHourPrice * 2);
    //         ans += hours/2;
    //         tmp = Math.min(comboPrice2H, extraHourPrice);
    //         ans += (hours  % 2) * tmp;
    //     }
    // }
}
