package com.project.hotel.controller;

import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.entity.*;
import com.project.hotel.service.*;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class AdminBookingController {
    private final RoomGroupService roomGroupService;
    private final CustomerService customerService;
    private final RoomService roomService;
    private final ServiceService serviceService;
    private final BedService bedService;
    private final FilesStorageService filesStorageService;
    private final RoomImageService roomImageService;
    private final ReviewService reviewService;
    private final BookingService bookingService;


    @GetMapping("/admin/calendar")
    public String showCalendar(Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Lấy tất cả các booking
        List<Booking> bookings = bookingService.findAll();
        List<BookingDto> bookingDtos = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingDto bookingDto = new BookingDto();
            bookingDto.setCheckInDate(booking.getCheckInDate());
            bookingDto.setCheckOutDate(booking.getCheckOutDate());

            String checkInDateStr = bookingDto.getCheckInDate();
            LocalDate checkInDate = LocalDate.parse(checkInDateStr, formatter);
//            bookingDto.setFormattedCheckInDate(checkInDate);

            String checkOutDateStr = bookingDto.getCheckOutDate();
            LocalDate checkOutDate = LocalDate.parse(checkOutDateStr, formatter);


            // Chuyển LocalDate thành java.util.Date
            Date date = java.util.Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date date1 = java.util.Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            bookingDto.setFormattedCheckInDate(date);
            bookingDto.setFormattedCheckOutDate(date1);
            bookingDtos.add(bookingDto);
        }
        model.addAttribute("bookings", bookingDtos);
        model.addAttribute("currentMonth", LocalDate.now().getMonthValue());
        model.addAttribute("daysInMonth", getDaysInMonth(LocalDate.now()));
        return "admin-calendar"; // Tên của view
    }
    private List<Integer> getDaysInMonth(LocalDate date) {
        int lengthOfMonth = date.lengthOfMonth();
        List<Integer> days = new ArrayList<>();
        for (int i = 1; i <= lengthOfMonth; i++) {
            days.add(i);
        }
        return days;
    }


    private List<Map<String, Object>> getDaysInMonth(int month, int year, List<Booking> bookings) {
        List<Map<String, Object>> daysInMonth = new ArrayList<>();

        // Lấy ngày đầu tháng và số ngày trong tháng
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        int daysInMonthCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Lặp qua các ngày trong tháng
        for (int i = 1; i <= daysInMonthCount; i++) {
            Map<String, Object> dayInfo = new HashMap<>();
            dayInfo.put("day", i);
            List<Booking> dayBookings = new ArrayList<>();

            // Kiểm tra các booking cho ngày hiện tại
            for (Booking booking : bookings) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String checkInDate = booking.getCheckInDate();
                String checkOutDate = booking.getCheckOutDate();

                // Kiểm tra booking có trong ngày này không
                if (checkInDate != null && checkOutDate != null) {
                    if (isDateInRange(i, month, year, checkInDate) || isDateInRange(i, month, year, checkOutDate)) {
                        dayBookings.add(booking);
                    }
                }
            }
            dayInfo.put("bookings", dayBookings);
            daysInMonth.add(dayInfo);
        }

        return daysInMonth;
    }

    private boolean isDateInRange(int day, int month, int year, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        try {
            Date bookingDate = sdf.parse(date);
            return !bookingDate.before(cal.getTime());
        } catch (Exception e) {
            return false;
        }
    }
//    @GetMapping("/admin/booking/online")
//    public String adminBookingOnline(Model model, Principal principal) {
//        LocalDate today = LocalDate.now();
//        LocalDate tomorrow = today.plusDays(1);
//        LocalTime checkInTime = LocalTime.of(14, 0);
//        LocalTime checkOutTime = LocalTime.of(11, 0);
//        int adults = 2;
//        int children = 0;
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        BookingDto bookingDto = new BookingDto();
//        bookingDto.setCheckInDate(today.format(dateFormatter));
//        bookingDto.setCheckOutDate(tomorrow.format(dateFormatter));
//        bookingDto.setCheckInTime(checkInTime.toString());
//        bookingDto.setCheckOutTime(checkOutTime.toString());
//        bookingDto.setAdults(adults);
//        bookingDto.setChildren(children);
//        model.addAttribute("bookingDto", bookingDto);
//        return "admin-booking-online";
//    }


    @GetMapping("/admin/booking/online")
    public String adminBookingOnline(Model model, Principal principal,
                                     @RequestParam(value = "checkin-date", required = false) String checkInDate,
                                     @RequestParam(value = "checkout-date", required = false) String checkOutDate,
                                     @RequestParam(value = "checkin-time", required = false) String checkInTime,
                                     @RequestParam(value = "checkout-time", required = false) String checkOutTime,
                                     @RequestParam(value = "adults", required = false) Integer adults,
                                     @RequestParam(value = "children", required = false) Integer children) {

        // Nếu không có giá trị tìm kiếm, sử dụng giá trị mặc định
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        if (checkInDate == null || checkOutDate == null || checkInTime == null || checkOutTime == null || adults == null || children == null) {
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1);
            LocalTime defaultCheckInTime = LocalTime.of(14, 0);
            LocalTime defaultCheckOutTime = LocalTime.of(11, 0);
            int defaultAdults = 2;
            int defaultChildren = 0;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Set giá trị mặc định vào BookingDto
            BookingDto bookingDto = new BookingDto();
            bookingDto.setCheckInDate(today.format(dateFormatter));
            bookingDto.setCheckOutDate(tomorrow.format(dateFormatter));
            bookingDto.setCheckInTime(defaultCheckInTime.toString());
            bookingDto.setCheckOutTime(defaultCheckOutTime.toString());
            bookingDto.setAdults(defaultAdults);
            bookingDto.setChildren(defaultChildren);

            for (RoomGroup roomGroup : roomGroups) {
                long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);

            }

            System.out.println("1");
            System.out.println(bookingDto);
            model.addAttribute("bookingDto", bookingDto);
        } else {
            // Nếu có tìm kiếm, sử dụng các giá trị từ form
            BookingDto bookingDto = new BookingDto();
            bookingDto.setCheckInDate(checkInDate);
            bookingDto.setCheckOutDate(checkOutDate);
            bookingDto.setCheckInTime(checkInTime);
            bookingDto.setCheckOutTime(checkOutTime);
            bookingDto.setAdults(adults);
            bookingDto.setChildren(children);

            System.out.println("2");
            System.out.println(bookingDto);
            model.addAttribute("bookingDto", bookingDto);
            List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
            model.addAttribute("availableRooms", availableRooms);
        }
        model.addAttribute("roomGroups", roomGroups);

        return "admin-booking-online";
    }


//    @PostMapping("/searchCustomer")
//    public String searchCustomer(@RequestParam String email, Model model) {
//        // Tìm khách hàng theo email
//        Customer customer = customerService.findByEmail(email);
//
//        if (customer == null) {
//            model.addAttribute("error", "Không tìm thấy khách hàng với email này");
//        } else {
//            model.addAttribute("customer", customer);  // Thêm thông tin khách hàng vào model
//        }
//
//        return "redirect:/admin/booking/online";
//    }


    @GetMapping(("/admin/booking/available/{roomGroupId}"))
    public String listRoomAvailableFromRoomGroup(@PathVariable("roomGroupId") Long roomGroupId, Model model, @RequestParam("checkInDate") String checkInDate,
                                                 @RequestParam("checkOutDate") String checkOutDate, @RequestParam("checkInTime") String checkInTime,
                                                 @RequestParam("checkOutTime") String checkOutTime, @RequestParam("adults") int adults,
                                                 @RequestParam("children") int children
    ) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setAdults(adults);
        bookingDto.setChildren(children);
        bookingDto.setCheckInDate(checkInDate);
        bookingDto.setCheckOutDate(checkOutDate);
        bookingDto.setCheckInTime(checkInTime);
        bookingDto.setCheckOutTime(checkOutTime);
        List<RoomGroup> roomGroupAvailable = new ArrayList<>();
        List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
        RoomGroup roomGroup = roomGroupService.findById(roomGroupId);
        long availableRoomCount = roomGroup.getRooms().stream()
                .filter(room -> availableRooms.contains(room))
                .count();

        long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
        System.out.println(priceDateTime);
//        System.out.println("Room Group: " + roomGroup.getGroupName() +
//                " has " + availableRoomCount + " available rooms.");
        roomGroup.setAvailableRoomCount(availableRoomCount);
        roomGroup.setPriceDateTime(priceDateTime);
        if(availableRoomCount > 0){
            roomGroupAvailable.add(roomGroup);
        }

        List<Review> reviews = reviewService.getReviewsByRoomGroupId(roomGroupId);
        model.addAttribute("bookingDto", bookingDto);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("roomGroup", roomGroup);
        model.addAttribute("priceDateTime", priceDateTime);
        model.addAttribute("reviews", reviews);

        System.out.println(roomGroupId);
        System.out.println(checkInDate);
        System.out.println(checkOutDate);
        System.out.println(checkInTime);
        System.out.println(checkOutTime);
        System.out.println(adults);
        System.out.println(children);
        return "room-detail";
    }
//
//
//    @PostMapping("/admin/user/edit")
//    public String adminUpdateUserList(@ModelAttribute("user") Customer userForm, Principal principal, Model model) {
//        Customer customer = customerService.findById(userForm.getCustomerId());
//        customer.setFullName(userForm.getFullName());
//        customer.setEmail(userForm.getEmail());
//        customer.setDateOfBirth(userForm.getDateOfBirth());
//        customer.setPhoneNumber(userForm.getPhoneNumber());
//        customer.setAddress(userForm.getAddress());
//        customerService.save(customer);
//        model.addAttribute("message", "Cập nhật thông tin thành công!");
//        model.addAttribute("user", customer);
//        return "redirect:/admin/user/list";
//    }
}