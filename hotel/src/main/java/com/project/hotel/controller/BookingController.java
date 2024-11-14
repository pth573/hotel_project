package com.project.hotel.controller;
import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.entity.*;
import com.project.hotel.model.enumType.BookingStatus;
import com.project.hotel.service.BookingService;
import com.project.hotel.service.CustomerService;
import com.project.hotel.service.RoomGroupService;
import com.project.hotel.service.RoomService;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final RoomService roomService;
    private final CustomerService customerService;
    private final RoomGroupService roomGroupService;
    private final BookingService bookingService;
    @GetMapping("/room-booking")
    public String roomHTML(@ModelAttribute("bookingDto") BookingDto bookingDto,  Model model, Principal principal) {


        System.out.println(bookingDto);

        CustomerUtils.getCustomerInfo(principal, customerService, model);
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        model.addAttribute("roomGroups", roomGroups);
        for (RoomGroup roomGroup : roomGroups) {
            System.out.println(roomGroup);
        }
        if(bookingDto == null){
            bookingDto = new BookingDto();
            bookingDto.setAdults(2);
        }
//        BookingDto bookingDto = new BookingDto();
        model.addAttribute("bookingDto", bookingDto);
        return "room-booking";

    }

    @PostMapping("/book-room")
    public String handleBookingForm(
            @ModelAttribute("bookingDto") BookingDto bookingDto,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        List<Room> roomListAvailable = roomService.findRoomAvailable(bookingDto);
        System.out.println("List sz:" + roomListAvailable.size());
        for(Room room : roomListAvailable){
            System.out.println("Cac phong trong: " + room.getRoomName() + " " + room.getRoomGroup().getGroupName());
        }
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        List<RoomGroup> roomGroupAvailable = new ArrayList<>();
        for (RoomGroup roomGroup : roomGroups) {
            if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
                List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
                long availableRoomCount = roomGroup.getRooms().stream()
                        .filter(room -> availableRooms.contains(room))
                        .count();
                long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
                System.out.println(priceDateTime);
                System.out.println("Room Group: " + roomGroup.getGroupName() +
                                   " has " + availableRoomCount + " available rooms.");
                roomGroup.setAvailableRoomCount(availableRoomCount);
                roomGroup.setPriceDateTime(priceDateTime);

                if(availableRoomCount > 0){
                    roomGroupAvailable.add(roomGroup);
                }
            }
        }
        model.addAttribute("bookingDto", bookingDto);
        model.addAttribute("roomGroups", roomGroupAvailable);
        model.addAttribute("adults", bookingDto.getAdults());
        model.addAttribute("children", bookingDto.getChildren());
        model.addAttribute("checkInDate", bookingDto.getCheckInDate());
        model.addAttribute("checkOutDate", bookingDto.getCheckOutDate());
        model.addAttribute("checkInTime", bookingDto.getCheckInTime());
        model.addAttribute("checkOutTime", bookingDto.getCheckOutTime());
        return "room-booking";
    }

    @GetMapping(("/room-group-list-available/{roomGroupId}"))
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
        model.addAttribute("bookingDto", bookingDto);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("roomGroup", roomGroup);
        model.addAttribute("priceDateTime", priceDateTime);
        System.out.println(roomGroupId);
        System.out.println(checkInDate);
        System.out.println(checkOutDate);
        System.out.println(checkInTime);
        System.out.println(checkOutTime);
        System.out.println(adults);
        System.out.println(children);
        return "room-detail";
    }

    @GetMapping("/payment")
    public String handleBooking(@ModelAttribute BookingDto bookingDto,
                                @RequestParam("room") Long roomId, Model model, Principal principal) {
        Room selectedRoom = roomService.findById(roomId);
        long priceDateTime = roomGroupService.calculatePrice(bookingDto, selectedRoom.getRoomGroup());
        String gmail = principal.getName();
        Customer customer = customerService.findByEmail(gmail);
        Booking booking = new Booking();
        booking.setRoom(selectedRoom);
        booking.setUser(customer);
        booking.setCheckInDate(bookingDto.getCheckInDate() + " " + bookingDto.getCheckInTime() + ":00");
        booking.setCheckOutDate(bookingDto.getCheckOutDate() + " " + bookingDto.getCheckOutTime() + ":00");
        booking.setTotalPrice(priceDateTime);
        booking.setStatus(BookingStatus.PENDING);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        model.addAttribute("booking", booking);
        model.addAttribute("currentDate", currentDate);
        return "payment";
    }

    @PostMapping("/payment")
    public String confirmBooking(@ModelAttribute("booking") Booking booking, Model model, Principal principal) {
        System.out.println("Booking khi gửi lên: " + booking);
        Booking bookingDB = bookingService.save(booking);
        Room room = roomService.findById(booking.getRoom().getRoomId());
        String gmail = principal.getName();
        Customer customer = customerService.findByEmail(gmail);
        bookingDB.setRoom(room);
        bookingDB.setUser(customer);
        model.addAttribute("booking", bookingDB);

        return "payment-confirm";
    }

}
