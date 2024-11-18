package com.project.hotel.controller;

import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.entity.Customer;
import com.project.hotel.model.entity.Role;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.Service;
import com.project.hotel.service.RoomGroupService;
import com.project.hotel.service.RoomService;
import com.project.hotel.service.ServiceService;
import com.project.hotel.service.CustomerService;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CustomerService customerService;
    private final RoomService roomService;
    private final RoomGroupService roomGroupService;
    private final ServiceService serviceService;

    private Logger logger = Logger.getLogger(getClass().getName());

    @GetMapping("/")
    public String showHome(Model model, Principal principal) {
        CustomerUtils.getCustomerInfo(principal, customerService, model);

        if(principal != null) {
            String gmail = principal.getName();
            Customer customer = customerService.findByEmail2(gmail);
            List<Role> roles = (List<Role>) customer.getRoles();
            for (Role role : roles) {
                System.out.println(role.getName());
                if(role.getName().equals("ADMIN")) {
                    return "redirect:/admin/room-group";
                }
            }
        }

        List<RoomGroup> roomGroups = roomGroupService.findAll();
        model.addAttribute("roomGroups", roomGroups);

        List<Service> serviceTop5List = serviceService.getTop5Services();
        model.addAttribute("top5Service", serviceTop5List);


        BookingDto bookingDto = new BookingDto();
        model.addAttribute("bookingDto", bookingDto);

        return "index";
    }

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

//    @GetMapping("/single-blog")
//    public String singleBlogHTML() {
//        return "single-blog";
//    }

    @GetMapping("/about")
    public String aboutHTML() {
        return "about";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

}
