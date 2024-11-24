package com.project.hotel.controller;

import com.project.hotel.model.entity.Booking;
import com.project.hotel.model.entity.Customer;
import com.project.hotel.service.BookingService;
import com.project.hotel.service.CustomerService;
import com.project.hotel.service.RoomGroupService;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final RoomGroupService roomGroupService;
    private final CustomerService customerService;
    private final BookingService bookingService;

    @GetMapping("/admin")
    public String getDailyPayments(Model model) {
        List<Booking> bookingList = bookingService.findAll();
        Map<String, Long> dailyPayments = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Booking booking : bookingList) {
            if (booking.getCheckInDate() != null) {
                LocalDate checkinLocalDate = LocalDate.parse(booking.getCheckInDate(), formatter);
                String day = checkinLocalDate.toString();

                Long amountPaid = booking.getAmountHasPaid();
                if (amountPaid != null) {
                    dailyPayments.put(day, dailyPayments.getOrDefault(day, 0L) + amountPaid);
                } else {
                    dailyPayments.put(day, dailyPayments.getOrDefault(day, 0L));
                }

            }
        }

        model.addAttribute("dailyPayments", dailyPayments);

        return "index-admin";
    }


    @GetMapping("/list")
    public String list(Model model, Principal principal) {

        return "list-product";
    }

    @GetMapping("/list1")
    public String list1(Model model, Principal principal) {

        return "product-details-tmp";
    }

    @GetMapping("/list2")
    public String list2(Model model, Principal principal) {

        return "index2";
    }

    @GetMapping("/list3")
    public String list3(Model model, Principal principal) {

        return "add-product";
    }

}
