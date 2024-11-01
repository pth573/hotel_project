package com.project.hotel.controller;

import com.project.hotel.model.dto.UserDto;
import com.project.hotel.model.entity.User;
import com.project.hotel.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class AuthController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("page", "Home");
        model.addAttribute("userDto", new UserDto()); // Khởi tạo UserDto
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("userDto") UserDto userDto,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Login");
            model.addAttribute("page", "Home");
            return "login";
        }
        System.out.println(userService.isValidUser(userDto));
        if (userService.isValidUser(userDto)) {

            model.addAttribute("message", "Login Successful");
            return "redirect:/index";
        } else {
            model.addAttribute("title", "Login");
            model.addAttribute("page", "Home");
            model.addAttribute("error", "Invalid email or password");
            return "login"; // Trả về lại form nếu đăng nhập không thành công
        }
    }


    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("page", "Register");
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(@Valid @ModelAttribute("userDto") UserDto userDto,
                                   BindingResult bindingResult,
                                   Model model){
        try{
            if(bindingResult.hasErrors()){
                model.addAttribute("userDto", userDto);
                return "register";
            }
            String email = userDto.getEmail();
            Optional<User> user = userService.findUserByEmail(email);
            if(user.isPresent()){
                model.addAttribute("userDto", userDto);
                model.addAttribute("error", "Email has been register!");
                return "register";
            }

            if(userDto.getPassword().equals(userDto.getConfirmedPassword())){
                userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
                userService.saveUser(userDto);
                model.addAttribute("success", "Register Successfully!");
            }else{
                model.addAttribute("error", "Password is incorrect!");
                model.addAttribute("userDto", userDto);
                return "register";
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Server is error, please try again later!");
        }
        return "register";
    }
}
