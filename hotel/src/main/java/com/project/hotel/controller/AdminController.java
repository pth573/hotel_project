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
        List<Booking> bookingList = bookingService.findAll(); // Lấy tất cả các booking
        Map<String, Long> dailyPayments = new HashMap<>();

        // Định dạng chuỗi checkinDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Duyệt qua danh sách booking và tính tổng thanh toán theo ngày checkinDate
        for (Booking booking : bookingList) {
            if (booking.getCheckInDate() != null) {  // Kiểm tra checkinDate không null
                // Phân tích chuỗi checkinDate thành đối tượng LocalDate
                LocalDate checkinLocalDate = LocalDate.parse(booking.getCheckInDate(), formatter);

                // Lấy ngày theo định dạng yyyy-MM-dd
                String day = checkinLocalDate.toString();  // toString() sẽ trả về định dạng "yyyy-MM-dd"

                // Cộng dồn số tiền thanh toán vào ngày tương ứng
                dailyPayments.put(day, dailyPayments.getOrDefault(day, 0L) + booking.getAmountHasPaid());
            }
        }

        model.addAttribute("dailyPayments", dailyPayments);

        return "index-admin"; // Trả về Map ngày và số tiền thanh toán
    }


    @GetMapping("/list")
    public String list(Model model, Principal principal) {
//        model.addAttribute("title", "Admin Page");
//        CustomerUtils.getCustomerInfo(principal, customerService, model);
//        Customer customer = (Customer) model.getAttribute("customer");

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
