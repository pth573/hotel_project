package com.project.hotel.controller;

import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.Service;
import com.project.hotel.service.RoomGroupService;
import com.project.hotel.service.RoomService;
import com.project.hotel.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminRoomController {
    private final RoomService roomService;
    private final RoomGroupService roomGroupService;
    private final ServiceService serviceService;

    @GetMapping("/admin/room")
    public String getRoomGroupList(Model model) {
        List<Room> roomList = roomService.findAll();
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("roomGroups", roomGroupService.findAll());
        model.addAttribute("roomList", roomList);
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



}
