package com.project.hotel.controller;

import com.project.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.Service;
import com.project.hotel.repository.RoomGroupRepository;
import com.project.hotel.repository.ServiceRepository;
import com.project.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomGroupRepository roomGroupRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("/rooms/manager")
    public String manageRoom() {
        return "room-manager";
    }

    @GetMapping("/room/list")
    public String getRoomGroupList(Model model) {
        List<Room> roomList = roomService.findAll();
        for (Room room : roomList) {
            System.out.println(room);
        }
        model.addAttribute("roomList", roomList);
        return "list-room";
    }

    @GetMapping("/room/update/{roomId}")
    public String showUpdateForm(@PathVariable Long roomId, Model model) {
        Room room = roomService.findById(roomId);
        model.addAttribute("room", room);
        List<RoomGroup> roomGroups = roomGroupRepository.findAll();
        List<Service> services = serviceRepository.findAll();
        model.addAttribute("room", room);
        model.addAttribute("roomGroups", roomGroups);
        model.addAttribute("services", services);
        return "room-update";
    }

    @PostMapping("/room/update/{roomId}")
    public String updateRoom(@PathVariable Long roomId, @ModelAttribute Room updatedRoom) {
        roomService.updateRoom(roomId, updatedRoom);
        return "redirect:/room/list";
    }

    @GetMapping("/room/delete/{roomId}")
    public String deleteRoom(@PathVariable Long roomId) {
        roomService.deleteById(roomId);
        return "redirect:/room/list";
    }

    @GetMapping("/room/add")
    public String showRoomForm(Model model) {
        Room room = new Room();
        List<RoomGroup> roomGroups = roomGroupRepository.findAll();
        List<Service> services = serviceRepository.findAll();
        model.addAttribute("room", room);
        model.addAttribute("roomGroups", roomGroups);
        model.addAttribute("services", services);
        return "add-room";
    }

    @PostMapping("/room/add")
    public String addRoom(@ModelAttribute Room room) {
        roomService.saveRoom(room);
        return "redirect:/room/list";
    }
}
