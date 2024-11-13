package com.project.hotel.controller;

import com.project.hotel.model.dto.CustomerDto;
import com.project.hotel.model.entity.Customer;
import com.project.hotel.model.entity.Role;
import com.project.hotel.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;

    private Logger logger = Logger.getLogger(getClass().getName());

    @GetMapping("/login")
    public String showMyLoginPage(Model model, Principal principal) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/register")
    public String showMyRegistrationPage(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        model.addAttribute("title", "Register");
        return "register-form";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                            BindingResult bindingResult,
                                            HttpSession session, Model model) {
        String email = customerDto.getEmail();
        logger.info("Processing registration form for: " + email);
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerDto", customerDto);
            return "register-form";
        }

        Optional<Customer> existing = customerService.findUserByEmail(email);
        if (existing.isPresent()) {
            model.addAttribute("customerDto", customerDto);
            model.addAttribute("registrationError", "User name already exists.");
            logger.warning("User name already exists.");
            return "register-form";
        }
        customerService.save(customerDto);
        logger.info("Successfully created user: " + email);
        session.setAttribute("customerDto", customerDto);
        return "login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }
}
