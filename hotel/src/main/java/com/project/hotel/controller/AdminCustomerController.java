package com.project.hotel.controller;

import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.dto.BookingDto3;
import com.project.hotel.model.dto.CustomerDto;
import com.project.hotel.model.entity.*;
import com.project.hotel.model.enumType.BedType;
import com.project.hotel.service.*;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
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


    @GetMapping("/admin/user/list")
    public String adminUserList(Model model, Principal principal, Locale locale) {
        CustomerUtils.getCustomerInfo(principal, customerService, model);
        List<Customer> users = customerService.findAll();
        List<CustomerDto> customerDtos = users.stream()
                .map(customer -> {
                    // Chuyển đổi Booking sang BookingDto3
                    List<BookingDto3> bookingDtos = customer.getBookings().stream()
                            .map(booking -> new BookingDto3(
                                    booking.getBookingId(),
                                    booking.getTotalPrice(),
                                    booking.getAmountHasPaid()))
                            .collect(Collectors.toList());

                    // Tính tổng số tiền đã trả từ tất cả bookings
                    Long totalAmountPaid = customer.getBookings().stream()
                            .mapToLong(Booking::getAmountHasPaid)
                            .sum();

                    Long totalAmountBooking = customer.getBookings().stream()
                            .mapToLong(Booking::getTotalPrice)
                            .sum();

                    // Chuyển đổi Customer thành CustomerDto
                    return CustomerDto.builder()
                            .customerId(customer.getCustomerId())
                            .fullName(customer.getFullName())
                            .email(customer.getEmail())
                            .phoneNumber(customer.getPhoneNumber())
                            .address(customer.getAddress())
                            .dateOfBirth(customer.getDateOfBirth())
                            .password(customer.getPassword())  // Nếu cần thiết
                            .bookings(bookingDtos)
                            .totalAmountPaid(totalAmountPaid)
                            .totalAmountBooking(totalAmountBooking)
                            .build();
                })
                .collect(Collectors.toList());

        // Thêm danh sách CustomerDto vào model
        model.addAttribute("users", customerDtos);

//        model.addAttribute("users", users);
        return "admin-user-list";
    }

//    @GetMapping("/room-booking")
//    public String showBookingForm(Model model) {
//        LocalDate today = LocalDate.now();
//        LocalDate tomorrow = today.plusDays(1);
//        LocalTime checkInTime = LocalTime.of(14, 0);
//        LocalTime checkOutTime = LocalTime.of(11, 0);
//        int adults = 2;
//        int children = 0;
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        // Tạo đối tượng BookingDto và thiết lập các giá trị
//        BookingDto bookingDto = new BookingDto();
//        bookingDto.setCheckInDate(today.format(dateFormatter));
//        bookingDto.setCheckOutDate(tomorrow.format(dateFormatter));
//        bookingDto.setCheckInTime(checkInTime.toString());
//        bookingDto.setCheckOutTime(checkOutTime.toString());
//        bookingDto.setAdults(adults);
//        bookingDto.setChildren(children);
//
//        // Thêm đối tượng bookingDto vào model để truyền cho view
//        model.addAttribute("bookingDto", bookingDto);
//
//        return "booking-form"; // trả về tên view (có thể là JSP hoặc Thymeleaf)
//    }

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

//        List<Object[]> reviewsWithGroup = reviewService.findReviewsWithSorting(groupName, sortBy);

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
        // Ghi nhận trả lời vào database


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
