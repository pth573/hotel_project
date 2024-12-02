package com.project.hotel.controller;

import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.dto.BookingDto3;
import com.project.hotel.model.dto.CustomerDto;
import com.project.hotel.model.entity.*;
import com.project.hotel.model.enumType.BedType;
import com.project.hotel.service.*;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AdminCustomerController {

    private final RoomGroupService roomGroupService;
    private final CustomerService customerService;
    private final RoomService roomService;
    private final ServiceService serviceService;
    private final BedService bedService;
    private final FilesStorageService filesStorageService;
    private final RoomImageService roomImageService;
    private final ReviewService reviewService;


//    @GetMapping("/admin/user/list")
//    public String adminUserList(Model model, Principal principal, Locale locale) {
//        CustomerUtils.getCustomerInfo(principal, customerService, model);
//        List<Customer> users = customerService.findAll();
//        List<CustomerDto> customerDtos = users.stream()
//                .map(customer -> {
//                    List<BookingDto3> bookingDtos = customer.getBookings().stream()
//                            .map(booking -> new BookingDto3(
//                                    booking.getBookingId(),
//                                    booking.getTotalPrice(),
//                                    booking.getAmountHasPaid()))
//                            .collect(Collectors.toList());
//                    Long totalAmountPaid = customer.getBookings().stream()
//                            .mapToLong(booking -> booking.getAmountHasPaid() != null ? booking.getAmountHasPaid() : 0L)
//                            .sum();
//                    Long totalAmountBooking = customer.getBookings().stream()
//                            .mapToLong(booking -> booking.getTotalPrice() != null ? booking.getTotalPrice() : 0L)
//                            .sum();
//                    return CustomerDto.builder()
//                            .customerId(customer.getCustomerId())
//                            .fullName(customer.getFullName())
//                            .email(customer.getEmail())
//                            .phoneNumber(customer.getPhoneNumber())
//                            .address(customer.getAddress())
//                            .dateOfBirth(customer.getDateOfBirth())
//                            .password(customer.getPassword())
//                            .bookings(bookingDtos)
//                            .totalAmountPaid(totalAmountPaid)
//                            .totalAmountBooking(totalAmountBooking)
//                            .build();
//                })
//                .collect(Collectors.toList());
//        model.addAttribute("users", customerDtos);
//        return "admin-user-list";
//    }


    @GetMapping("/admin/user/list")
    public String adminUserList(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size,
                                Model model, Principal principal, Locale locale) {
        // Lấy thông tin khách hàng
        CustomerUtils.getCustomerInfo(principal, customerService, model);

        // Phân trang danh sách người dùng
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> userPage = customerService.findAll(pageable);

        // Chuyển đổi danh sách khách hàng sang DTO
        List<CustomerDto> customerDtos = userPage.getContent().stream()
                .map(customer -> {
                    List<BookingDto3> bookingDtos = customer.getBookings().stream()
                            .map(booking -> new BookingDto3(
                                    booking.getBookingId(),
                                    booking.getTotalPrice(),
                                    booking.getAmountHasPaid()))
                            .collect(Collectors.toList());
                    Long totalAmountPaid = customer.getBookings().stream()
                            .mapToLong(booking -> booking.getAmountHasPaid() != null ? booking.getAmountHasPaid() : 0L)
                            .sum();
                    Long totalAmountBooking = customer.getBookings().stream()
                            .mapToLong(booking -> booking.getTotalPrice() != null ? booking.getTotalPrice() : 0L)
                            .sum();
                    return CustomerDto.builder()
                            .customerId(customer.getCustomerId())
                            .fullName(customer.getFullName())
                            .email(customer.getEmail())
                            .phoneNumber(customer.getPhoneNumber())
                            .address(customer.getAddress())
                            .dateOfBirth(customer.getDateOfBirth())
                            .password(customer.getPassword())
                            .bookings(bookingDtos)
                            .totalAmountPaid(totalAmountPaid)
                            .totalAmountBooking(totalAmountBooking)
                            .build();
                })
                .collect(Collectors.toList());

        // Thêm thông tin vào model
        model.addAttribute("users", customerDtos);
        model.addAttribute("userPage", userPage);
        return "admin-user-list";
    }



    @PostMapping("/admin/user/edit")
    public String adminUpdateUserList(@ModelAttribute("user") Customer userForm, Principal principal, Model model) {
        Customer customer = customerService.findById(userForm.getCustomerId());
        customer.setFullName(userForm.getFullName());
        customer.setEmail(userForm.getEmail());
        customer.setDateOfBirth(userForm.getDateOfBirth());
        customer.setPhoneNumber(userForm.getPhoneNumber());
        customer.setAddress(userForm.getAddress());
        customerService.save(customer);
        model.addAttribute("message", "Cập nhật thông tin thành công!");
        model.addAttribute("user", customer);
        return "redirect:/admin/user/list";
    }

    @PostMapping("/admin/user/delete/{customerId}")
    public String adminDeleteUserList(@PathVariable("customerId") Long customerId, RedirectAttributes redirectAttributes) {
        customerService.deleteById(customerId);
        return "redirect:/admin/user/list";
    }




    @GetMapping("/admin/user/booking")
    public String adminUserBooking(Model model, Principal principal, Locale locale) {
        CustomerUtils.getCustomerInfo(principal, customerService, model);

        RoomGroup roomGroup = new RoomGroup();
        List<Bed> bedList = new ArrayList<>();
        BedType[] bedTypes = BedType.values();
        for (BedType bedType : bedTypes) {
            bedList.add(new Bed(bedType, 0));
        }
        roomGroup.setBeds(bedList);
        List<Service> services = serviceService.findAll();
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        model.addAttribute("roomGroup", roomGroup);
        model.addAttribute("services", services);
        model.addAttribute("roomGroups", roomGroups);
        return "admin-user-list";
    }

    @GetMapping("/admin/user/review")
    public String getReviews(
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "hasReplies", required = false) String hasReplies,
            Model model) {

        List<Object[]> reviewsWithGroup = reviewService.findReviewsWithSorting(groupName, sortBy, hasReplies);

        List<RoomGroup> roomGroups = roomGroupService.findAll();

        model.addAttribute("reviewsWithGroup", reviewsWithGroup);
        model.addAttribute("roomGroups", roomGroups);
        model.addAttribute("selectedGroup", groupName == null ? "" : groupName);
        model.addAttribute("sortBy", sortBy == null ? "newest" : sortBy);
        model.addAttribute("hasReplies", hasReplies);

        return "admin-user-review";
    }

    @PostMapping("/admin/user/review/reply")
    public String replyToReview(@RequestParam("reviewId") Long reviewId,
                                @RequestParam("replyContent") String replyContent,
                                Model model, Principal principal) {
        String email = principal.getName();
        Customer user = customerService.findByEmail2(email);
        Review review = reviewService.findById(reviewId);
        Reply reply = new Reply();
        reply.setCreatedAt(LocalDateTime.now());
        reply.setUser(user);
        reply.setContent(replyContent);
        List<Reply> replies = review.getReplies();
        if(replies == null){
            replies = new ArrayList<>();
        }
        replies.add(reply);
        reply.setReview(review);
        reply.setUser(user);
        reviewService.save(review);
        model.addAttribute("message", "Trả lời đánh giá thành công!");
        return "redirect:/admin/user/review";
    }


}
