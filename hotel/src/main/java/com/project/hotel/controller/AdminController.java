package com.project.hotel.controller;

import com.project.hotel.model.entity.Customer;
import com.project.hotel.service.CustomerService;
import com.project.hotel.service.RoomGroupService;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final RoomGroupService roomGroupService;
    private final CustomerService customerService;

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("title", "Admin Page");
        CustomerUtils.getCustomerInfo(principal, customerService, model);
        Customer customer = (Customer) model.getAttribute("customer");

        return "index-admin";
    }


}
