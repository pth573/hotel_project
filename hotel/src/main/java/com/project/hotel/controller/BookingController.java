package com.project.hotel.controller;
import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.entity.Customer;
import com.project.hotel.model.entity.Role;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final RoomService roomService;
    private final CustomerService customerService;
    private final RoomGroupService roomGroupService;

    @GetMapping("/room-booking")
    public String roomHTML(Model model, Principal principal) {
        CustomerUtils.getCustomerInfo(principal, customerService, model);
//        List<Room> rooms = roomService.findAll();
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        model.addAttribute("roomGroups", roomGroups);
        BookingDto bookingDto = new BookingDto();
        bookingDto.setAdults(2);
        model.addAttribute("bookingDto", bookingDto);

        return "room-booking3";

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

        // Duyệt qua từng RoomGroup
        for (RoomGroup roomGroup : roomGroups) {

            if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
                            // Lấy danh sách các phòng trống trong RoomGroup cho khoảng thời gian đặt phòng
                List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);

                // Đếm số lượng phòng trống trong RoomGroup
                long availableRoomCount = roomGroup.getRooms().stream()
                        .filter(room -> availableRooms.contains(room))
                        .count();

                long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
                System.out.println(priceDateTime);

                // In ra thông tin về nhóm phòng và số lượng phòng trống
                System.out.println("Room Group: " + roomGroup.getGroupName() +
                                   " has " + availableRoomCount + " available rooms.");

                roomGroup.setAvailableRoomCount(availableRoomCount);
                roomGroup.setPriceDateTime(priceDateTime);

                if(availableRoomCount > 0){
                    roomGroupAvailable.add(roomGroup);
                }
            }
        }
        // BookingRequest bookingRequest2 = new BookingRequest();
        model.addAttribute("bookingDto", bookingDto);
        model.addAttribute("roomGroups", roomGroupAvailable);
        model.addAttribute("adults", bookingDto.getAdults());
        model.addAttribute("children", bookingDto.getChildren());
        model.addAttribute("checkInDate", bookingDto.getCheckInDate());
        model.addAttribute("checkOutDate", bookingDto.getCheckOutDate());
        model.addAttribute("checkInTime", bookingDto.getCheckInTime());
        model.addAttribute("checkOutTime", bookingDto.getCheckOutTime());

//        return "redirect:/room-booking";
        return "room-booking3";
    }

//    @PostMapping(("/room-group-list-available/{roomGroupId}"))
//    public String listRoomAvailableFromRoomGroup(@RequestParam("roomGroupId") Long roomGroupId, Model model, @ModelAttribute("bookingRequest") BookingRequest bookingRequest) {
//        System.out.println(bookingRequest.getCheckInDate());
//        System.out.println(bookingRequest.getCheckOutDate());
//        System.out.println(bookingRequest.getCheckInTime());
//        System.out.println(bookingRequest.getCheckOutTime());
//        System.out.println(bookingRequest.getAdults());
//        System.out.println(bookingRequest.getChildren());
//    }
}
