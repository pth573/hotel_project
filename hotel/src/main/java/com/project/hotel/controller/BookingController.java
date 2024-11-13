package com.project.hotel.controller;
import com.project.hotel.model.entity.BookingRequest;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.service.BookingService;
import com.project.hotel.service.RoomGroupService;
import com.project.hotel.service.RoomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final RoomService roomService;


    private final RoomGroupService roomGroupService;

    @GetMapping("/room-booking")
    public String roomHTML(Model theModel, Principal principal) {
//        List<Room> rooms = roomService.findAll();
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        theModel.addAttribute("roomGroups", roomGroups);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setAdults(2);
        theModel.addAttribute("bookingRequest", bookingRequest);

        return "room-booking3";

    }

    @PostMapping("/book-room")
    public String handleBookingForm(
            @ModelAttribute("bookingRequest") BookingRequest bookingRequest,
            Model model,
            RedirectAttributes redirectAttributes
    ) {


        // ok
        System.out.println(bookingRequest.getCheckInDate());
        System.out.println(bookingRequest.getCheckOutDate());
        System.out.println(bookingRequest.getCheckInTime());
        System.out.println(bookingRequest.getCheckOutTime());
        System.out.println(bookingRequest.getAdults());
        System.out.println(bookingRequest.getChildren());
        System.out.println("Hi");
        System.out.println(bookingRequest);

        List<Room> roomListAvailable = roomService.findRoomAvailable(bookingRequest);
        System.out.println("List sz:" + roomListAvailable.size());
        for(Room room : roomListAvailable){
            System.out.println("Cac phong trong: " + room.getRoomName() + " " + room.getRoomGroup().getGroupName());
        }

        List<RoomGroup> roomGroups = roomGroupService.findAll();

        List<RoomGroup> roomGroupAvailable = new ArrayList<>();

        // Duyệt qua từng RoomGroup
        for (RoomGroup roomGroup : roomGroups) {

            if(bookingRequest.getChildren() + bookingRequest.getAdults() <= roomGroup.getMaxOccupancy()){
                            // Lấy danh sách các phòng trống trong RoomGroup cho khoảng thời gian đặt phòng
                List<Room> availableRooms = roomService.findRoomAvailable(bookingRequest);

                // Đếm số lượng phòng trống trong RoomGroup
                long availableRoomCount = roomGroup.getRooms().stream()
                        .filter(room -> availableRooms.contains(room))
                        .count();

                long priceDateTime = roomGroupService.calculatePrice(bookingRequest, roomGroup);
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
        model.addAttribute("bookingRequest", bookingRequest);
        model.addAttribute("roomGroups", roomGroupAvailable);
        model.addAttribute("adults", bookingRequest.getAdults());
        model.addAttribute("children", bookingRequest.getChildren());
        model.addAttribute("checkInDate", bookingRequest.getCheckInDate());
        model.addAttribute("checkOutDate", bookingRequest.getCheckOutDate());
        model.addAttribute("checkInTime", bookingRequest.getCheckInTime());
        model.addAttribute("checkOutTime", bookingRequest.getCheckOutTime());

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
