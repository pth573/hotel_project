package com.project.hotel.controller;

import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.dto.RoomDTO;
import com.project.hotel.model.dto.RoomGroupDTO;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.Service;
import com.project.hotel.model.enumType.RoomStatus;
import com.project.hotel.service.RoomGroupService;
import com.project.hotel.service.RoomService;
import com.project.hotel.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminRoomController {
    private final RoomService roomService;
    private final RoomGroupService roomGroupService;
    private final ServiceService serviceService;

//    @GetMapping("/admin/room")
//    public String getRoomGroupList(Model model) {
//        List<Room> roomList = roomService.findAll();
//        Room room = new Room();
//        model.addAttribute("room", room);
//        model.addAttribute("roomGroups", roomGroupService.findAll());
//        model.addAttribute("roomList", roomList);
//        return "admin-room";
//    }

    @GetMapping("/admin/room")
    public String getRoomGroupList(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                   Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomService.findAll(pageable);
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("roomList", roomPage);
        model.addAttribute("roomGroups", roomGroupService.findAll());
        return "admin-room";
    }

    @GetMapping("/admin/room/add")
    public String showRoomForm(Model model) {
        Room room = new Room();
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        if(roomGroups == null || roomGroups.size() == 0) {
            roomGroups = new ArrayList<RoomGroup>();
        }
        List<Service> services = serviceService.findAll();
        model.addAttribute("room", room);
        model.addAttribute("roomGroups", roomGroups);
        model.addAttribute("services", services);
        return "admin-room";
    }

    @PostMapping("/admin/room/add")
    public String addRoom(@ModelAttribute Room room) {
        roomService.saveRoom(room);
        return "redirect:/admin/room";
    }




    @GetMapping("/admin/room/update/{roomId}")
    public String showUpdateForm(@PathVariable Long roomId, Model model) {
        Room room = roomService.findById(roomId);
        model.addAttribute("room", room);
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        List<Service> services = serviceService.findAll();
        model.addAttribute("room", room);
        model.addAttribute("roomGroups", roomGroups);
        model.addAttribute("services", services);
        return "admin-room-update";
    }

    @PostMapping("/admin/room/update/{roomId}")
    public String updateRoom(@PathVariable Long roomId, @ModelAttribute Room updatedRoom) {
        roomService.updateRoom(roomId, updatedRoom);
        return "redirect:/admin/room";
    }

    @GetMapping("/admin/room/delete/{roomId}")
    public String deleteRoom(@PathVariable Long roomId) {
        roomService.deleteById(roomId);
        return "redirect:/admin/room";
    }


    @GetMapping("/admin/room/status")
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
//            List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
            List<Room> roomList = roomService.findAll();
            List<RoomDTO> availableRoomDTOs = new ArrayList<>();
            for(Room room : roomList) {
                RoomGroup roomGroup = room.getRoomGroup();
                if(roomGroup.getGroupName().equals(roomGroupRequest)){
                    if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
                        long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
                        RoomDTO roomDTO = new RoomDTO();
                        roomDTO.setRoomName(room.getRoomName());
                        roomDTO.setRoomId(room.getRoomId());
                        roomDTO.setDescription(room.getDescription());
                        roomDTO.setTotalPrice(priceDateTime);
                        boolean status = roomService.isRoomAvailable(room.getRoomId(), bookingDto);
                        if(status){
                            roomDTO.setRoomStatus(RoomStatus.AVAILABLE);
                        }
                        else {
                            roomDTO.setRoomStatus(RoomStatus.BOOKED);
                        }
                        System.out.println("Hi");
                        System.out.println(roomDTO.getRoomId() + " " + roomDTO.getRoomStatus().getDisplayName());
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
//            List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
            List<Room> roomList = roomService.findAll();
            List<RoomDTO> availableRoomDTOs = new ArrayList<>();
            for(Room room : roomList) {
                RoomGroup roomGroup = room.getRoomGroup();
                if(roomGroup.getGroupName().equals(roomGroupRequest)){
                    if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
                        long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
                        RoomDTO roomDTO = new RoomDTO();
                        roomDTO.setRoomName(room.getRoomName());
                        roomDTO.setRoomId(room.getRoomId());
                        roomDTO.setDescription(room.getDescription());
                        roomDTO.setTotalPrice(priceDateTime);
                        boolean status = roomService.isRoomAvailable(room.getRoomId(), bookingDto);
                        if(status){
                            roomDTO.setRoomStatus(RoomStatus.AVAILABLE);
                        }
                        else {
                            roomDTO.setRoomStatus(RoomStatus.BOOKED);
                        }
                        System.out.println("Hi1");
                        System.out.println(roomDTO.getRoomId() + " " + roomDTO.getRoomStatus().getDisplayName());
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
        return "admin-room-status";
    }

//    @GetMapping("/admin/room/status")
//    public String adminBookingOnline(Model model, Principal principal,
//                                     @RequestParam(value = "checkin-date", required = false) String checkInDate,
//                                     @RequestParam(value = "checkout-date", required = false) String checkOutDate,
//                                     @RequestParam(value = "checkin-time", required = false) String checkInTime,
//                                     @RequestParam(value = "checkout-time", required = false) String checkOutTime,
//                                     @RequestParam(value = "adults", required = false) Integer adults,
//                                     @RequestParam(value = "children", required = false) Integer children,
//                                     @RequestParam(value = "roomGroup", required = false) String roomGroupRequest
//    ) {
//
//        List<RoomGroup> roomGroups = roomGroupService.findAll();
//        if (checkInDate == null || checkOutDate == null || checkInTime == null || checkOutTime == null || adults == null || children == null) {
//            LocalDate today = LocalDate.now();
//            LocalDate tomorrow = today.plusDays(1);
//            LocalTime defaultCheckInTime = LocalTime.of(14, 0);
//            LocalTime defaultCheckOutTime = LocalTime.of(11, 0);
//            int defaultAdults = 2;
//            int defaultChildren = 0;
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//            // Set giá trị mặc định vào BookingDto
//            BookingDto bookingDto = new BookingDto();
//            bookingDto.setCheckInDate(today.format(dateFormatter));
//            bookingDto.setCheckOutDate(tomorrow.format(dateFormatter));
//            bookingDto.setCheckInTime(defaultCheckInTime.toString());
//            bookingDto.setCheckOutTime(defaultCheckOutTime.toString());
//            bookingDto.setAdults(defaultAdults);
//            bookingDto.setChildren(defaultChildren);
//
//            model.addAttribute("bookingDto", bookingDto);
//            List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
//            List<RoomDTO> availableRoomDTOs = new ArrayList<>();
//            for(Room room : availableRooms) {
//                RoomGroup roomGroup = room.getRoomGroup();
//                if(roomGroup.getGroupName().equals(roomGroupRequest)){
//                    if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
//                        long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
//                        RoomDTO roomDTO = new RoomDTO();
//                        roomDTO.setRoomName(room.getRoomName());
//                        roomDTO.setRoomId(room.getRoomId());
//                        roomDTO.setDescription(room.getDescription());
//                        roomDTO.setTotalPrice(priceDateTime);
//
//                        RoomGroupDTO roomGroupDTO = new RoomGroupDTO();
//                        roomGroupDTO.setGroupName(roomGroup.getGroupName());
//                        roomDTO.setRoomGroup(roomGroupDTO);
//                        availableRoomDTOs.add(roomDTO);
//                    }
//                }
//            }
//
//            System.out.println("1");
//            System.out.println(bookingDto);
//            model.addAttribute("bookingDto", bookingDto);
//        } else {
//            // Nếu có tìm kiếm, sử dụng các giá trị từ form
//            BookingDto bookingDto = new BookingDto();
//            bookingDto.setCheckInDate(checkInDate);
//            bookingDto.setCheckOutDate(checkOutDate);
//            bookingDto.setCheckInTime(checkInTime);
//            bookingDto.setCheckOutTime(checkOutTime);
//            bookingDto.setAdults(adults);
//            bookingDto.setChildren(children);
//            System.out.println(bookingDto);
//            model.addAttribute("bookingDto", bookingDto);
//            List<RoomGroup> roomGroupAvailable = new ArrayList<>();
//            for (RoomGroup roomGroup : roomGroups) {
//                if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
//                    List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
//                    long availableRoomCount = roomGroup.getRooms().stream()
//                            .filter(room -> availableRooms.contains(room))
//                            .count();
//                    long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
//                    System.out.println(priceDateTime);
//                    System.out.println("Room Group: " + roomGroup.getGroupName() +
//                            " has " + availableRoomCount + " available rooms.");
//                    roomGroup.setAvailableRoomCount(availableRoomCount);
//                    roomGroup.setPriceDateTime(priceDateTime);
//                    if(availableRoomCount > 0){
//                        roomGroupAvailable.add(roomGroup);
//                    }
//                }
//            }
//            model.addAttribute("availableRoomGroups", roomGroupAvailable);
//        }
//        model.addAttribute("roomGroups", roomGroups);
//
//        return "admin-room-status";
//    }


//    @GetMapping("/admin/room/status")
////    @PostMapping("/admin/booking/online")
//    public String adminBookingOnline(Model model, Principal principal,
//                                     @RequestParam(value = "checkin-date", required = false) String checkInDate,
//                                     @RequestParam(value = "checkout-date", required = false) String checkOutDate,
//                                     @RequestParam(value = "checkin-time", required = false) String checkInTime,
//                                     @RequestParam(value = "checkout-time", required = false) String checkOutTime,
//                                     @RequestParam(value = "adults", required = false) Integer adults,
//                                     @RequestParam(value = "children", required = false) Integer children,
//                                     @RequestParam(value = "roomGroup", required = false) String roomGroupRequest
//    ) {
//
//        List<RoomGroup> roomGroups = roomGroupService.findAll();
//        if (checkInDate == null || checkOutDate == null || checkInTime == null || checkOutTime == null || adults == null || children == null) {
//            LocalDate today = LocalDate.now();
//            LocalDate tomorrow = today.plusDays(1);
//            LocalTime defaultCheckInTime = LocalTime.of(14, 0);
//            LocalTime defaultCheckOutTime = LocalTime.of(11, 0);
//            int defaultAdults = 2;
//            int defaultChildren = 0;
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//            // Set giá trị mặc định vào BookingDto
//            BookingDto bookingDto = new BookingDto();
//            bookingDto.setCheckInDate(today.format(dateFormatter));
//            bookingDto.setCheckOutDate(tomorrow.format(dateFormatter));
//            bookingDto.setCheckInTime(defaultCheckInTime.toString());
//            bookingDto.setCheckOutTime(defaultCheckOutTime.toString());
//            bookingDto.setAdults(defaultAdults);
//            bookingDto.setChildren(defaultChildren);
//
//            model.addAttribute("bookingDto", bookingDto);
//            List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
//            List<RoomDTO> availableRoomDTOs = new ArrayList<>();
//            for(Room room : availableRooms) {
//                RoomGroup roomGroup = room.getRoomGroup();
//                if(roomGroup.getGroupName().equals(roomGroupRequest)){
//                    if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
//                        long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
//                        RoomDTO roomDTO = new RoomDTO();
//                        roomDTO.setRoomName(room.getRoomName());
//                        roomDTO.setRoomId(room.getRoomId());
//                        roomDTO.setDescription(room.getDescription());
//                        roomDTO.setTotalPrice(priceDateTime);
//
//                        RoomGroupDTO roomGroupDTO = new RoomGroupDTO();
//                        roomGroupDTO.setGroupName(roomGroup.getGroupName());
//                        roomDTO.setRoomGroup(roomGroupDTO);
//                        availableRoomDTOs.add(roomDTO);
//                    }
//                }
//            }
//
//            System.out.println("1");
//            System.out.println(bookingDto);
//            model.addAttribute("bookingDto", bookingDto);
//        } else {
//            // Nếu có tìm kiếm, sử dụng các giá trị từ form
//            BookingDto bookingDto = new BookingDto();
//            bookingDto.setCheckInDate(checkInDate);
//            bookingDto.setCheckOutDate(checkOutDate);
//            bookingDto.setCheckInTime(checkInTime);
//            bookingDto.setCheckOutTime(checkOutTime);
//            bookingDto.setAdults(adults);
//            bookingDto.setChildren(children);
//
//            System.out.println("2");
//            System.out.println(bookingDto);
//            model.addAttribute("bookingDto", bookingDto);
////            List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
////
////            List<RoomDTO> availableRoomDTOs = new ArrayList<>();
////            for(Room room : availableRooms) {
////                RoomGroup roomGroup = room.getRoomGroup();
////                if(roomGroup.getGroupName().equals(roomGroupRequest)){
////                    if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
////                        long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
////                        RoomDTO roomDTO = new RoomDTO();
////                        roomDTO.setRoomName(room.getRoomName());
////                        roomDTO.setRoomId(room.getRoomId());
////                        roomDTO.setDescription(room.getDescription());
////                        roomDTO.setTotalPrice(priceDateTime);
////
////                        RoomGroupDTO roomGroupDTO = new RoomGroupDTO();
////                        roomGroupDTO.setGroupName(roomGroup.getGroupName());
////                        roomDTO.setRoomGroup(roomGroupDTO);
////                        availableRoomDTOs.add(roomDTO);
////                    }
////                }
////            }
//
////            List<RoomGroup> roomGroups = roomGroupService.findAll();
//            List<RoomGroup> roomGroupAvailable = new ArrayList<>();
//            for (RoomGroup roomGroup : roomGroups) {
//                if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
//                    List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
//                    long availableRoomCount = roomGroup.getRooms().stream()
//                            .filter(room -> availableRooms.contains(room))
//                            .count();
//                    long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
//                    System.out.println(priceDateTime);
//                    System.out.println("Room Group: " + roomGroup.getGroupName() +
//                            " has " + availableRoomCount + " available rooms.");
//                    roomGroup.setAvailableRoomCount(availableRoomCount);
//                    roomGroup.setPriceDateTime(priceDateTime);
//                    if(availableRoomCount > 0){
//                        roomGroupAvailable.add(roomGroup);
//                    }
//                }
//            }
////            model.addAttribute("roomGroups", roomGroupAvailable);
//            model.addAttribute("availableRoomGroups", roomGroupAvailable);
//        }
//        model.addAttribute("roomGroups", roomGroups);
//
//        return "admin-room-status";
//    }




}
