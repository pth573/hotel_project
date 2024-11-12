package com.project.hotel.controller;

import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.Service;
import com.project.hotel.service.RoomGroupService;
import com.project.hotel.service.RoomService;
import com.project.hotel.service.ServiceService;
import com.project.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomGroupService roomGroupService;

    @Autowired
    private ServiceService serviceService;

    private Logger logger = Logger.getLogger(getClass().getName());

//    @GetMapping("/room-booking")
//    public String roomHTML(Model theModel, Principal principal) {
////        List<Room> rooms = roomService.findAll();
//        List<RoomGroup> roomGroups = roomGroupService.findAll();
//        theModel.addAttribute("roomGroups", roomGroups);
//        return "room-booking";
//
//    }

    @GetMapping("/room-detail")
    public String roomSingleHTML() {

        return "room-detail";
    }

    @GetMapping("/contact")
    public String contactHTML() {
        return "contact";
    }

    @GetMapping("/blog")
    public String blogHTML() {
        return "blog";
    }

    @GetMapping("/single-blog")
    public String singleBlogHTML() {
        return "single-blog";
    }

    @GetMapping("/about")
    public String aboutHTML() {
        return "about";
    }


    @GetMapping("/admin")
    public String showAdminPage() {
        return "index2";
    }


    @GetMapping("/login")
    public String showMyLoginPage(Model model, Principal principal) {
        if(principal != null) {
            model.addAttribute("user", userService.findByEmail(principal.getName()));
        }
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/")
    public String showHome(Model model, Principal principal) {
        if(principal != null) {
            model.addAttribute("user", userService.findByEmail(principal.getName()));
            System.out.println(model.getAttribute("user"));
        }
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        model.addAttribute("roomGroups", roomGroups);

        List<Service> serviceTop5List = serviceService.getTop5Services();
        model.addAttribute("top5Service", serviceTop5List);
        return "index";
//        String email = principal.getName();
//        System.out.println(email);
//        User user = userService.findByEmail(email);
//        if(user.getRole().equals(Role.USER)) {
//            return "index";
//        }
//        else {
//            return "admin-page";
//        }
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

}
