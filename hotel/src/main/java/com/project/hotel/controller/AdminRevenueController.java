package com.project.hotel.controller;

import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.entity.*;
import com.project.hotel.model.enumType.BedType;
import com.project.hotel.service.*;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class AdminRevenueController {

    private final RoomGroupService roomGroupService;
    private final CustomerService customerService;
    private final RoomService roomService;
    private final ServiceService serviceService;
    private final BedService bedService;
    private final FilesStorageService filesStorageService;
    private final RoomImageService roomImageService;
    private final ReviewService reviewService;
    private final BookingService bookingService;


    @GetMapping("/admin/report/revenue")
    public String showRevenueStatsPage(Model model) {
        model.addAttribute("startDate", "");
        model.addAttribute("endDate", "");
        return "admin-report-revenue"; // Show the form for input
    }

    @PostMapping("/admin/report/revenue")
    public String getRevenueStats(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            Model model) {

        // Get the list of bookings between the start and end date
        List<Booking> bookings = bookingService.findBookingsByDateRange(startDate, endDate);


        // Calculate the total revenue
        Long totalRevenue = bookings.stream()
                .mapToLong(Booking::getTotalPrice)
                .sum();



        // Add the results to the model to show in the view
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("bookings", bookings);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "admin-report-revenue";
    }


    @GetMapping("/admin/report/room/status")
    public String showReportRoomStatus(Model model, Principal principal,
                                       @RequestParam(value = "checkin-date", required = false) String checkInDate,
                                       @RequestParam(value = "checkout-date", required = false) String checkOutDate,
                                       @RequestParam(value = "checkin-time", required = false) String checkInTime,
                                       @RequestParam(value = "checkout-time", required = false) String checkOutTime,
                                       @RequestParam(value = "roomGroup", required = false) String roomGroupName) {

        // Lấy danh sách nhóm phòng
        List<RoomGroup> roomGroups = roomGroupService.findAll();

        // Xử lý giá trị mặc định
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalTime defaultCheckInTime = LocalTime.of(14, 0);
        LocalTime defaultCheckOutTime = LocalTime.of(11, 0);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        BookingDto bookingDto = new BookingDto();

        if (checkInDate == null || checkOutDate == null || checkInTime == null || checkOutTime == null) {
            bookingDto.setCheckInDate(today.format(dateFormatter));
            bookingDto.setCheckOutDate(tomorrow.format(dateFormatter));
            bookingDto.setCheckInTime(defaultCheckInTime.toString());
            bookingDto.setCheckOutTime(defaultCheckOutTime.toString());
        } else {
            bookingDto.setCheckInDate(checkInDate);
            bookingDto.setCheckOutDate(checkOutDate);
            bookingDto.setCheckInTime(checkInTime);
            bookingDto.setCheckOutTime(checkOutTime);
        }

        model.addAttribute("bookingDto", bookingDto);

        // Xử lý danh sách phòng trống
        List<Room> availableRooms;
        if (roomGroupName == null || roomGroupName.isEmpty()) {
            // Không chọn gì, mặc định là tất cả phòng
            availableRooms = roomService.findRoomAvailable(bookingDto);
        } else {
            // Lọc theo nhóm phòng
            RoomGroup roomGroup = roomGroupService.findByGroupName(roomGroupName);
            if (roomGroup != null) {
                availableRooms = roomService.findRoomAvailableByGroup(bookingDto, roomGroup);
            } else {
                availableRooms = Collections.emptyList();
            }
        }

        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("roomGroups", roomGroups);
        model.addAttribute("selectedRoomGroupName", roomGroupName);

        return "admin-report-room-status";
    }

//    @GetMapping("/admin/report/room/status")
//    public String showReportRoomStatus(Model model, Principal principal,
//                                       @RequestParam(value = "checkin-date", required = false) String checkInDate,
//                                       @RequestParam(value = "checkout-date", required = false) String checkOutDate,
//                                       @RequestParam(value = "checkin-time", required = false) String checkInTime,
//                                       @RequestParam(value = "checkout-time", required = false) String checkOutTime,
//                                       @RequestParam(value = "roomGroup", required = false) String roomGroupName) {
//
//        // Lấy danh sách nhóm phòng
//        List<RoomGroup> roomGroups = roomGroupService.findAll();
//
//        // Xử lý giá trị mặc định
//        LocalDate today = LocalDate.now();
//        LocalDate tomorrow = today.plusDays(1);
//        LocalTime defaultCheckInTime = LocalTime.of(14, 0);
//        LocalTime defaultCheckOutTime = LocalTime.of(11, 0);
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        BookingDto bookingDto = new BookingDto();
//
//        if (checkInDate == null || checkOutDate == null || checkInTime == null || checkOutTime == null) {
//            bookingDto.setCheckInDate(today.format(dateFormatter));
//            bookingDto.setCheckOutDate(tomorrow.format(dateFormatter));
//            bookingDto.setCheckInTime(defaultCheckInTime.toString());
//            bookingDto.setCheckOutTime(defaultCheckOutTime.toString());
//        } else {
//            bookingDto.setCheckInDate(checkInDate);
//            bookingDto.setCheckOutDate(checkOutDate);
//            bookingDto.setCheckInTime(checkInTime);
//            bookingDto.setCheckOutTime(checkOutTime);
//        }
//
//        model.addAttribute("bookingDto", bookingDto);
//
//        // Lấy nhóm phòng và phòng trống
//        RoomGroup roomGroup = roomGroupService.findByGroupName(roomGroupName);
//        if (roomGroup == null) {
//            roomGroup = new RoomGroup();
//        }
//        model.addAttribute("roomGroup", roomGroup);
//
//        List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
//        model.addAttribute("availableRooms", availableRooms);
//        model.addAttribute("roomGroups", roomGroups);
//
//        return "admin-report-room-status";
//    }
//


//    @GetMapping("/admin/report/room/status")
//    public String showReportRoomStatus(Model model, Principal principal,
//                                       @RequestParam(value = "checkin-date", required = false) String checkInDate,
//                                       @RequestParam(value = "checkout-date", required = false) String checkOutDate,
//                                       @RequestParam(value = "checkin-time", required = false) String checkInTime,
//                                       @RequestParam(value = "checkout-time", required = false) String checkOutTime,
//                                       @RequestParam(value = "roomGroup", required = false) String roomGroupName) {
//
//        System.out.println("roomGroupName: " + roomGroupName);
//
//        // Nếu không có giá trị tìm kiếm, sử dụng giá trị mặc định
//        List<RoomGroup> roomGroups = roomGroupService.findAll();
//        if (checkInDate == null || checkOutDate == null || checkInTime == null || checkOutTime == null) {
//            LocalDate today = LocalDate.now();
//            LocalDate tomorrow = today.plusDays(1);
//            LocalTime defaultCheckInTime = LocalTime.of(14, 0);
//            LocalTime defaultCheckOutTime = LocalTime.of(11, 0);
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//            // Set giá trị mặc định vào BookingDto
//            BookingDto bookingDto = new BookingDto();
//            bookingDto.setCheckInDate(today.format(dateFormatter));
//            bookingDto.setCheckOutDate(tomorrow.format(dateFormatter));
//            bookingDto.setCheckInTime(defaultCheckInTime.toString());
//            bookingDto.setCheckOutTime(defaultCheckOutTime.toString());
//            List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
//            RoomGroup roomGroup = roomGroupService.findByGroupName(roomGroupName);
//
//            System.out.println("1: " + roomGroup);
//            if(roomGroup == null) {
//                roomGroup = new RoomGroup();
//            }
//            model.addAttribute("roomGroup", roomGroup);
//            model.addAttribute("availableRooms", availableRooms);
//            model.addAttribute("bookingDto", bookingDto);
//        } else {
//            // Nếu có tìm kiếm, sử dụng các giá trị từ form
//            BookingDto bookingDto = new BookingDto();
//            bookingDto.setCheckInDate(checkInDate);
//            bookingDto.setCheckOutDate(checkOutDate);
//            bookingDto.setCheckInTime(checkInTime);
//            bookingDto.setCheckOutTime(checkOutTime);
//            model.addAttribute("bookingDto", bookingDto);
//            RoomGroup roomGroup = roomGroupService.findByGroupName(roomGroupName);
//            System.out.println("2: " + roomGroup);
//            if(roomGroup == null) {
//                roomGroup = new RoomGroup();
//            }
//            model.addAttribute("roomGroup", roomGroup);
//            List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
//            model.addAttribute("availableRooms", availableRooms);
//        }
//        model.addAttribute("roomGroups", roomGroups);
//
//        return "admin-report-room-status";
//    }


//    @GetMapping("/admin/report/room/status")
//    public String showReportRoomStatus(Model model) {
//        model.addAttribute("startDate", "");
//        model.addAttribute("endDate", "");
//        return "admin-report-room-status"; // Show the form for input
//    }



}
