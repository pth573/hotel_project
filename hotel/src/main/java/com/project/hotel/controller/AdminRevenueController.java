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
        List<Booking> bookings = bookingService.findBookingsByDateRange(startDate, endDate);

        Long totalRevenue = bookings.stream()
                .mapToLong(Booking::getTotalPrice)
                .sum();

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
        List<RoomGroup> roomGroups = roomGroupService.findAll();

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

        List<Room> availableRooms;
        if (roomGroupName == null || roomGroupName.isEmpty()) {
            availableRooms = roomService.findRoomAvailable(bookingDto);
        } else {
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

}
