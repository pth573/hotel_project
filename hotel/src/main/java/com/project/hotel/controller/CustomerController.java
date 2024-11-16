package com.project.hotel.controller;

import com.project.hotel.model.entity.Booking;
import com.project.hotel.model.entity.Customer;
import com.project.hotel.model.entity.Review;
import com.project.hotel.service.BookingService;
import com.project.hotel.service.CustomerService;
import com.project.hotel.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final BookingService bookingService;
    private final ReviewService reviewService;

    private Logger logger = Logger.getLogger(getClass().getName());

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/1")
    public String search() {
        return "1";
    }


    @PostMapping("/searchCustomer")
    public ResponseEntity<?> searchCustomer(@RequestParam String email) {
        Customer customer = customerService.findByEmail(email);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.ok(null);
        }
    }

    // Tạo khách hàng mới
    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/1";
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

        @GetMapping("/user/booking/list")
        public String showUserBooking(Model model, Principal principal) {
            String email = principal.getName();
            Customer user = customerService.findByEmail(email);
            List<Booking> bookings = bookingService.findByBookingUser(user);
            model.addAttribute("user", user);
            model.addAttribute("bookings", bookings);
            Review review = new Review();
//            review.setRating(5);
            model.addAttribute("review", review);
            return "user-bookings";
        }

    @PostMapping("/user/booking/review/{bookingId}")
    public String addUserReview(@ModelAttribute("review") Review review,
                                @PathVariable("bookingId") Long bookingId,
                                Model model,
                                Principal principal) {
        String email = principal.getName();
        Customer user = customerService.findByEmail(email);
        if (user == null) {
            return "redirect:/";
        }
        Booking booking = bookingService.findById(bookingId);
        if (booking == null) {
            return "redirect:/user/booking/list";
        }
        review.setBooking(booking); // Set the booking for the review
        review.setUser(user); // Set the user who is reviewing
        review.setCreatedAt(LocalDateTime.now()); // Set the current time for the review
        reviewService.save(review);
        return "redirect:/user/booking/list";
    }

}
