package com.project.hotel.controller;

import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.service.CustomerService;
import com.project.hotel.service.RoomGroupService;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminRoomGroupController {

    private final RoomGroupService roomGroupService;
    private final CustomerService customerService;

    @GetMapping("/admin/room-group")
    public String roomGroup(Model model, Principal principal) {
        model.addAttribute("title", "Admin Room Group");
        CustomerUtils.getCustomerInfo(principal, customerService, model);
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        model.addAttribute("roomGroups", roomGroups);
        model.addAttribute("size", roomGroups.size());
        model.addAttribute("new-roomGroup", new RoomGroup());
        return "admin-room-group";
    }

    @PostMapping("/admin/save-room-group")
    public String saveRoomGroup(@ModelAttribute("new-roomGroup") RoomGroup roomGroup, Model model, RedirectAttributes redirectAttributes) {
        try{
            model.addAttribute("new-roomGroup", roomGroup);
            redirectAttributes.addFlashAttribute("success", "Room Group saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Room Group save failed");
        }
        roomGroupService.save(roomGroup);
        return "redirect:/admin/room-group";
    }
}
