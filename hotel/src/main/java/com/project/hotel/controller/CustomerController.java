package com.project.hotel.controller;

import com.project.hotel.model.entity.Customer;
import com.project.hotel.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private Logger logger = Logger.getLogger(getClass().getName());

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/user/update-info")
    public String showUserForm(Model model, Principal principal) {
        String email = principal.getName();
        Customer user = customerService.findByEmail(email);
        model.addAttribute("user", user);
        return "user-info";
    }

    @PostMapping("/user/update-info")
    public String updateUserInfo(@ModelAttribute("user") Customer userForm, Principal principal, Model model) {
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);

        customer.setFullName(userForm.getFullName());
        customer.setDateOfBirth(userForm.getDateOfBirth());
        customer.setPhoneNumber(userForm.getPhoneNumber());
        customer.setAddress(userForm.getAddress());

        customerService.save(customer);

        model.addAttribute("message", "Cập nhật thông tin thành công!");
        model.addAttribute("user", customer);
        return "user-info";
    }
}
