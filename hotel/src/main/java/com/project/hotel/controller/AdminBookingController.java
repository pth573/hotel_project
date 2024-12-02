package com.project.hotel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hotel.model.dto.*;
import com.project.hotel.model.entity.*;
import com.project.hotel.model.enumType.BookingStatus;
import com.project.hotel.service.*;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

//    @GetMapping("/admin/calendar")
//    public String showCalendar(Model model) {
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        List<Booking> bookings = bookingService.findAll();
//        List<BookingDto> bookingDtos = new ArrayList<>();
//        for (Booking booking : bookings) {
//            BookingDto bookingDto = new BookingDto();
//            bookingDto.setCheckInDate(booking.getCheckInDate());
//            bookingDto.setCheckOutDate(booking.getCheckOutDate());
//
//            String checkInDateStr = bookingDto.getCheckInDate();
//            LocalDate checkInDate = LocalDate.parse(checkInDateStr, formatter);
//            String checkOutDateStr = bookingDto.getCheckOutDate();
//            LocalDate checkOutDate = LocalDate.parse(checkOutDateStr, formatter);
//
//            Date date = java.util.Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//            Date date1 = java.util.Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//            bookingDto.setFormattedCheckInDate(date);
//            bookingDto.setFormattedCheckOutDate(date1);
//            bookingDtos.add(bookingDto);
//        }
//        model.addAttribute("bookings", bookingDtos);
//        model.addAttribute("currentMonth", LocalDate.now().getMonthValue());
//        model.addAttribute("daysInMonth", getDaysInMonth(LocalDate.now()));
//        return "admin-calendar";
//    }

    @GetMapping("/admin/calendar")
    public String showCalendar(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Lấy danh sách các phòng
        List<Room> rooms = roomService.findAll();
        List<RoomDTO2> roomDtos = new ArrayList<>();

        for (Room room : rooms) {
            RoomDTO2 roomDto = new RoomDTO2();
            roomDto.setRoomId(room.getRoomId());
            roomDto.setRoomName(room.getRoomName());
            roomDto.setDescription(room.getDescription());

            // Thêm booking của phòng vào DTO
            List<BookingDto> bookingDtos = new ArrayList<>();
            for (Booking booking : room.getBookings()) {
                BookingDto bookingDto = new BookingDto();
                bookingDto.setRoomId(room.getRoomId());
                bookingDto.setCheckInDate(booking.getCheckInDate());
                bookingDto.setCheckOutDate(booking.getCheckOutDate());
                bookingDto.setAmountHasPaid(booking.getAmountHasPaid());
                bookingDto.setTotalPrice(booking.getTotalPrice());
                bookingDto.setStatus(booking.getStatus());

                // Format ngày
                String checkInDateStr = bookingDto.getCheckInDate();
                LocalDate checkInDate = LocalDate.parse(checkInDateStr, formatter);
                String checkOutDateStr = bookingDto.getCheckOutDate();
                LocalDate checkOutDate = LocalDate.parse(checkOutDateStr, formatter);

                bookingDto.setFormattedCheckInDate(java.util.Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                bookingDto.setFormattedCheckOutDate(java.util.Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

                // Lấy thông tin người dùng từ Booking và ánh xạ vào CustomerDto2
                if (booking.getUser() != null) {
                    CustomerDto2 customerDto = CustomerDto2.builder()
                            .customerId(booking.getUser().getCustomerId())
                            .email(booking.getUser().getEmail())
                            .fullName(booking.getUser().getFullName())
                            .phoneNumber(booking.getUser().getPhoneNumber())
                            .build();
                    bookingDto.setCustomerDto(customerDto);
                }

                bookingDtos.add(bookingDto);
            }

            roomDto.setBookingDtos(bookingDtos);
            roomDtos.add(roomDto);
        }

        model.addAttribute("rooms", roomDtos);
        model.addAttribute("currentMonth", LocalDate.now().getMonthValue());
        model.addAttribute("daysInMonth", getDaysInMonth(LocalDate.now()));
        return "admin-calendar";
    }



//    @GetMapping("/admin/calendar")
//    public String showCalendar(Model model) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        // Lấy danh sách các phòng
//        List<Room> rooms = roomService.findAll();
//        List<RoomDTO2> roomDtos = new ArrayList<>();
//
//        for (Room room : rooms) {
//            RoomDTO2 roomDto = new RoomDTO2();
//            roomDto.setRoomId(room.getRoomId());
//            roomDto.setRoomName(room.getRoomName());
//            roomDto.setDescription(room.getDescription());
//
//            // Thêm booking của phòng vào DTO
//            List<BookingDto> bookingDtos = new ArrayList<>();
//            for (Booking booking : room.getBookings()) {
//                BookingDto bookingDto = new BookingDto();
//                bookingDto.setRoomId(room.getRoomId());
//                bookingDto.setCheckInDate(booking.getCheckInDate());
//                bookingDto.setCheckOutDate(booking.getCheckOutDate());
//
//                // Format ngày
//                String checkInDateStr = bookingDto.getCheckInDate();
//                LocalDate checkInDate = LocalDate.parse(checkInDateStr, formatter);
//                String checkOutDateStr = bookingDto.getCheckOutDate();
//                LocalDate checkOutDate = LocalDate.parse(checkOutDateStr, formatter);
//
//                bookingDto.setFormattedCheckInDate(java.util.Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                bookingDto.setFormattedCheckOutDate(java.util.Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                bookingDtos.add(bookingDto);
//            }
//
//            roomDto.setBookingDtos(bookingDtos);
//            roomDtos.add(roomDto);
//        }
//
//        model.addAttribute("rooms", roomDtos);
//        model.addAttribute("currentMonth", LocalDate.now().getMonthValue());
//        model.addAttribute("daysInMonth", getDaysInMonth(LocalDate.now()));
//        return "admin-calendar";
//    }



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

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        int daysInMonthCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= daysInMonthCount; i++) {
            Map<String, Object> dayInfo = new HashMap<>();
            dayInfo.put("day", i);
            List<Booking> dayBookings = new ArrayList<>();

            for (Booking booking : bookings) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String checkInDate = booking.getCheckInDate();
                String checkOutDate = booking.getCheckOutDate();

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


    @GetMapping("/admin/booking/online")
//    @PostMapping("/admin/booking/online")
    public String adminBookingOnline(Model model, Principal principal,
                                     @RequestParam(value = "checkin-date", required = false) String checkInDate,
                                     @RequestParam(value = "checkout-date", required = false) String checkOutDate,
                                     @RequestParam(value = "checkin-time", required = false) String checkInTime,
                                     @RequestParam(value = "checkout-time", required = false) String checkOutTime,
                                     @RequestParam(value = "adults", required = false) Integer adults,
                                     @RequestParam(value = "children", required = false) Integer children,
                                     @RequestParam(value = "roomGroup", required = false) String roomGroupRequest
    ) {

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

            model.addAttribute("bookingDto", bookingDto);
            List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
            List<RoomDTO> availableRoomDTOs = new ArrayList<>();
            for(Room room : availableRooms) {
                RoomGroup roomGroup = room.getRoomGroup();
                if(roomGroup.getGroupName().equals(roomGroupRequest)){
                    if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
                        long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
                        RoomDTO roomDTO = new RoomDTO();
                        roomDTO.setRoomName(room.getRoomName());
                        roomDTO.setRoomId(room.getRoomId());
                        roomDTO.setDescription(room.getDescription());
                        roomDTO.setTotalPrice(priceDateTime);

                        RoomGroupDTO roomGroupDTO = new RoomGroupDTO();
                        roomGroupDTO.setGroupName(roomGroup.getGroupName());
                        roomDTO.setRoomGroup(roomGroupDTO);
                        availableRoomDTOs.add(roomDTO);
                    }
                }
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
            List<RoomDTO> availableRoomDTOs = new ArrayList<>();
            for(Room room : availableRooms) {
                RoomGroup roomGroup = room.getRoomGroup();
                if(roomGroup.getGroupName().equals(roomGroupRequest)){
                    if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
                        long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
                        RoomDTO roomDTO = new RoomDTO();
                        roomDTO.setRoomName(room.getRoomName());
                        roomDTO.setRoomId(room.getRoomId());
                        roomDTO.setDescription(room.getDescription());
                        roomDTO.setTotalPrice(priceDateTime);

                        RoomGroupDTO roomGroupDTO = new RoomGroupDTO();
                        roomGroupDTO.setGroupName(roomGroup.getGroupName());
                        roomDTO.setRoomGroup(roomGroupDTO);
                        availableRoomDTOs.add(roomDTO);
                    }
                }
            }
            model.addAttribute("availableRooms", availableRoomDTOs);
        }
        model.addAttribute("roomGroups", roomGroups);

        return "admin-booking-online";
    }

    @PostMapping("/find-customer")
    @ResponseBody
    public ResponseEntity<?> findCustomer(@RequestParam String email) {
        try {
            Customer customer = customerService.findByEmail(email);
            if (customer == null) {
                return ResponseEntity.ok("Customer not found");
            }
            CustomerDto customerDto = new CustomerDto();
            customerDto.setEmail(customer.getEmail());
            customerDto.setFullName(customer.getFullName());
            customerDto.setPhoneNumber(customer.getPhoneNumber());
            return ResponseEntity.ok(customerDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid email: " + email);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/create-customer")
    @ResponseBody
    public Customer createCustomer(@RequestParam String newEmail,
                                   @RequestParam String newFullName,
                                   @RequestParam String newPhoneNumber) {
        Customer customer = customerService.findByEmail2(newEmail);
        if(customer == null) {
            customer = new Customer();
        }
        customer.setEmail(newEmail);
        customer.setFullName(newFullName);
        customer.setPhoneNumber(newPhoneNumber);
        customerService.save(customer);
        return customer;
    }

    @PostMapping("/admin/booking/save")
    @ResponseBody
    public ResponseEntity<?> bookRoom(@RequestBody BookingDto bookingRequest, Principal principal) {

        if (bookingRequest.getRoomId() == null || bookingRequest.getEmail() == null) {
            System.out.println("NO");
            return ResponseEntity.badRequest().body("Thông tin không đầy đủ!");
        }

        Double totalPrice = Double.parseDouble(String.valueOf(bookingRequest.getTotalPrice()));
        Long totalPriceAsLong = totalPrice.longValue();

        Double amountHasPaid = Double.parseDouble(String.valueOf(bookingRequest.getAmountHasPaid()));
        Long amountHasPaidLong = Math.round(amountHasPaid);


        System.out.println("t: " + totalPriceAsLong + "a :" + amountHasPaidLong);
        Booking booking = new Booking();

        Room room = roomService.findById(bookingRequest.getRoomId());
        booking.setRoom(room);
        Customer customer = customerService.findByEmail2(bookingRequest.getEmail());
        if(customer == null) {
            System.out.println("Null");
        }
        else {
            System.out.println("OK");
            System.out.println(customer.getFullName());
            System.out.println(customer.getPhoneNumber());
            System.out.println(customer.getEmail());
        }

        System.out.println("A: "  + customer.getEmail());

        booking.setUser(customer);
        booking.setStatus(BookingStatus.ACCEPTED);
        Long a = Long.parseLong(String.valueOf(bookingRequest.getTotalPrice()));
        Long b = Long.parseLong(String.valueOf(bookingRequest.getAmountHasPaid()));
        booking.setTotalPrice(totalPriceAsLong);
        booking.setAmountHasPaid(amountHasPaidLong);
        booking.setCheckInDate(bookingRequest.getCheckInDate() + " " + bookingRequest.getCheckInTime() + ":00");
        booking.setCheckOutDate(bookingRequest.getCheckOutDate() + " " + bookingRequest.getCheckOutTime() + ":00");
        bookingService.save(booking);
        return ResponseEntity.ok().body("Đặt phòng thành công!");

    }


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

    @GetMapping("/admin/booking/order")
    public String adminGetBookingOrder(Model model, Principal principal) {
        model.addAttribute("title", "Admin Booking Order");
        CustomerUtils.getCustomerInfo(principal, customerService, model);

        List<Booking> bookings = bookingService.findAll();
        model.addAttribute("bookings", bookings);
        return "admin-booking-order";
    }

    @RequestMapping(value = "/admin/booking/order/update", method = RequestMethod.GET)
    public String adminUpdateBooking(@RequestParam("bookingId") Long bookingId,
                                     @RequestParam("status") String bookingStatusRequest,
                                     RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.findById(bookingId);
        BookingStatus bookingStatus = BookingStatus.valueOf(bookingStatusRequest);
        booking.setStatus(bookingStatus);
        bookingService.save(booking);
        redirectAttributes.addFlashAttribute("success", "Cập nhật thành công");
        return "redirect:/admin/booking/order";
    }

    @RequestMapping(value = "/admin/booking/order/delete", method = RequestMethod.GET)
    public String adminDeleteBooking(@RequestParam("bookingId") Long bookingId, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.findById(bookingId);
        booking.setStatus(BookingStatus.DELETED);
        bookingService.save(booking);
        redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        return "redirect:/admin/booking/order";
    }


    @GetMapping("/admin/booking/order/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Booking booking = bookingService.findById(id);
        model.addAttribute("booking", booking);
        model.addAttribute("statusOptions", BookingStatus.values());
        return "admin-booking-order-update";
    }


    @PostMapping("/admin/booking/order/update/{id}")
    public String updateBooking(@PathVariable("id") Long id, @ModelAttribute Booking booking, @RequestParam("status") String status) {
        BookingStatus bookingStatus = BookingStatus.valueOf(status);
        booking.setStatus(bookingStatus);
        bookingService.updateBooking(id, booking);
        return "redirect:/admin/booking/order";
    }

}
