package com.project.hotel.controller;

import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.User;
import com.project.hotel.model.enumType.Role;
import com.project.hotel.model.user.WebUser;
import com.project.hotel.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(getClass().getName());

//    @GetMapping("/room")
//    public String roomHTML() {
//        return "room";
//    }
//
//    @GetMapping("/single-room")
//    public String roomSingleHTML() {
//        return "single-room";
//    }
//
//    @GetMapping("/contact")
//    public String contactHTML() {
//        return "contact";
//    }
//
//    @GetMapping("/blog")
//    public String blogHTML() {
//        return "blog";
//    }
//
//    @GetMapping("/single-blog")
//    public String singleBlogHTML() {
//        return "single-blog";
//    }
//
//    @GetMapping("/about")
//    public String aboutHTML() {
//        return "about";
//    }
//
//
//    @GetMapping("/admin")
//    public String showAdminPage() {
//        return "index2";
//    }
//
//
//    @GetMapping("/login")
//    public String showMyLoginPage() {
//        return "login";
//    }
//
//    @GetMapping("/")
//    public String showHome(Model theModel, Principal principal) {
//        List<RoomGroup> roomGroups =
//
//        return "index";
////        String email = principal.getName();
////        System.out.println(email);
////        User user = userService.findByEmail(email);
////        if(user.getRole().equals(Role.USER)) {
////            return "index";
////        }
////        else {
////            return "admin-page";
////        }
//    }

//    @GetMapping("/access-denied")
//    public String showAccessDenied() {
//        return "access-denied";
//    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/register")
    public String showMyRegistrationPage(Model model) {
        model.addAttribute("webUser", new WebUser());
        model.addAttribute("title", "Register");
        return "register-form";
    }

    @PostMapping("/register")
    public String processRegistrationForm(
            @Valid @ModelAttribute("webUser") WebUser theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {
        String email = theWebUser.getEmail();
        logger.info("Processing registration form for: " + email);
        if (theBindingResult.hasErrors()){
            return "register-form";
        }

        Optional<User> existing = userService.findUserByEmail(email);
        if (existing.isPresent()){
            theModel.addAttribute("webUser", new WebUser());
            theModel.addAttribute("registrationError", "User name already exists.");
            logger.warning("User name already exists.");
            return "register-form";
        }
        userService.save(theWebUser);
        logger.info("Successfully created user: " + email);
        session.setAttribute("user", theWebUser);
        return "login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage(Model model) {
        model.addAttribute("webUser", new WebUser());
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @GetMapping("/user/update-info")
    public String showUserForm(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "user-info";
    }

    @PostMapping("/user/update-info")
    public String updateUserInfo(@ModelAttribute("user") User userForm, Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        user.setFullName(userForm.getFullName());
        user.setDateOfBirth(userForm.getDateOfBirth());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAddress(userForm.getAddress());

        userService.save(user);

        model.addAttribute("message", "Cập nhật thông tin thành công!");
        model.addAttribute("user", user);  // Trả lại thông tin đã cập nhật để hiển thị
        return "user-info";
    }

//    @GetMapping("/logout?")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        session.removeAttribute("user");
//        return "login";
//    }

//    @GetMapping("/user/update-info")
//    public String showUserForm(Model model) {
//        model.addAttribute("user", new User());
//        return "user-info";
//    }
//
//
//    @PostMapping("/user/update-info")
//    public void updateUserInfo(Model model, @ModelAttribute User userForm, Principal principal) {
//        String email = principal.getName();
//        System.out.println(email);
//        User user = userService.findByEmail(email);
//        System.out.println(user);
//        user.setPhoneNumber(userForm.getPhoneNumber());
//        user.setDateOfBirth(userForm.getDateOfBirth());
//        user.setFullName(userForm.getFullName());
//        user.setAddress(userForm.getAddress());
//        userService.save(user);
//    }
}
