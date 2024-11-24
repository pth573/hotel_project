package com.project.hotel.controller;
import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.entity.*;
import com.project.hotel.model.enumType.BookingStatus;
import com.project.hotel.service.*;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final RoomService roomService;
    private final CustomerService customerService;
    private final RoomGroupService roomGroupService;
    private final BookingService bookingService;
    private final ReviewService reviewService;
    private final ReplyService replyService;

    @GetMapping("/room-booking")
    public String roomHTML(@ModelAttribute("bookingDto") BookingDto bookingDto,  Model model, Principal principal) {


        System.out.println(bookingDto);

        CustomerUtils.getCustomerInfo(principal, customerService, model);
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        model.addAttribute("roomGroups", roomGroups);
        for (RoomGroup roomGroup : roomGroups) {
            System.out.println(roomGroup);
        }
        if(validateBookingInput(bookingDto.getCheckInDate(),
                                bookingDto.getCheckOutDate(),
                                bookingDto.getCheckInTime(),
                                bookingDto.getCheckOutTime(),
                                bookingDto.getAdults(),
                                bookingDto.getChildren())){
            bookingDto.setId(1);
        };
        if(bookingDto.getId() == 0){
            System.out.println(1);
            bookingDto = new BookingDto();
            bookingDto.setCheckInDate(LocalDate.now().toString());
            bookingDto.setCheckOutDate(LocalDate.now().plusDays(1).toString());
            bookingDto.setCheckInTime("14:00");
            bookingDto.setCheckOutTime("11:00");
            bookingDto.setAdults(2);
            bookingDto.setChildren(0);
            List<RoomGroup> roomGroupAvailable = new ArrayList<>();
            for (RoomGroup roomGroup : roomGroups) {
                if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
                    List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
                    long availableRoomCount = roomGroup.getRooms().stream()
                            .filter(room -> availableRooms.contains(room))
                            .count();
                    long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
                    System.out.println(priceDateTime);
                    System.out.println("Room Group: " + roomGroup.getGroupName() +
                            " has " + availableRoomCount + " available rooms.");
                    roomGroup.setAvailableRoomCount(availableRoomCount);
                    roomGroup.setPriceDateTime(priceDateTime);

                    if(availableRoomCount > 0){
                        roomGroupAvailable.add(roomGroup);
                    }
                }
            }
            model.addAttribute("bookingDto", bookingDto);
            model.addAttribute("roomGroups", roomGroupAvailable);
            model.addAttribute("adults", bookingDto.getAdults());
            model.addAttribute("children", bookingDto.getChildren());
            model.addAttribute("checkInDate", bookingDto.getCheckInDate());
            model.addAttribute("checkOutDate", bookingDto.getCheckOutDate());
            model.addAttribute("checkInTime", bookingDto.getCheckInTime());
            model.addAttribute("checkOutTime", bookingDto.getCheckOutTime());
        }
        model.addAttribute("adults", bookingDto.getAdults());
        model.addAttribute("children", bookingDto.getChildren());
        model.addAttribute("checkInDate", bookingDto.getCheckInDate());
        model.addAttribute("checkOutDate", bookingDto.getCheckOutDate());
        model.addAttribute("checkInTime", bookingDto.getCheckInTime());
        model.addAttribute("checkOutTime", bookingDto.getCheckOutTime());
        model.addAttribute("bookingDto", bookingDto);
        return "room-booking";

    }

    private boolean validateBookingInput(String checkInDate, String checkOutDate, String checkInTime, String checkOutTime, Integer adults, Integer children) {
        return checkInDate != null && checkOutDate != null && checkInTime != null && checkOutTime != null && adults != null && children != null;
    }

    @PostMapping("/book-room")
    public String handleBookingForm(
            @ModelAttribute("bookingDto") BookingDto bookingDto,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        List<Room> roomListAvailable = roomService.findRoomAvailable(bookingDto);
        System.out.println("List sz:" + roomListAvailable.size());
        for(Room room : roomListAvailable){
            System.out.println("Cac phong trong: " + room.getRoomName() + " " + room.getRoomGroup().getGroupName());
        }
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        List<RoomGroup> roomGroupAvailable = new ArrayList<>();
        for (RoomGroup roomGroup : roomGroups) {
            if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
                List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
                long availableRoomCount = roomGroup.getRooms().stream()
                        .filter(room -> availableRooms.contains(room))
                        .count();
                long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
                System.out.println(priceDateTime);
                System.out.println("Room Group: " + roomGroup.getGroupName() +
                                   " has " + availableRoomCount + " available rooms.");
                roomGroup.setAvailableRoomCount(availableRoomCount);
                roomGroup.setPriceDateTime(priceDateTime);

                if(availableRoomCount > 0){
                    roomGroupAvailable.add(roomGroup);
                }
            }
        }
        model.addAttribute("bookingDto", bookingDto);
        model.addAttribute("roomGroups", roomGroupAvailable);
        model.addAttribute("adults", bookingDto.getAdults());
        model.addAttribute("children", bookingDto.getChildren());
        model.addAttribute("checkInDate", bookingDto.getCheckInDate());
        model.addAttribute("checkOutDate", bookingDto.getCheckOutDate());
        model.addAttribute("checkInTime", bookingDto.getCheckInTime());
        model.addAttribute("checkOutTime", bookingDto.getCheckOutTime());
        return "room-booking";
    }

    @GetMapping("/room-group-list-available/{roomGroupId}")
    public String listRoomAvailableFromRoomGroup(
            @PathVariable("roomGroupId") Long roomGroupId,
            Model model,
            @RequestParam(value = "checkInDate", required = false, defaultValue = "#{T(java.time.LocalDate).now().toString()}") String checkInDate,
            @RequestParam(value = "checkOutDate", required = false, defaultValue = "#{T(java.time.LocalDate).now().plusDays(1).toString()}") String checkOutDate,
            @RequestParam(value = "checkInTime", required = false, defaultValue = "14:00") String checkInTime,
            @RequestParam(value = "checkOutTime", required = false, defaultValue = "11:00") String checkOutTime,
            @RequestParam(value = "adults", required = false, defaultValue = "2") int adults,
            @RequestParam(value = "children", required = false, defaultValue = "0") int children
    ) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setAdults(adults);
        bookingDto.setChildren(children);
        bookingDto.setCheckInDate(checkInDate);
        bookingDto.setCheckOutDate(checkOutDate);
        bookingDto.setCheckInTime(checkInTime);
        bookingDto.setCheckOutTime(checkOutTime);

        RoomGroup roomGroup = roomGroupService.findById(roomGroupId);

        if (roomGroup == null) {
            return "redirect:/error?message=Room group not found";
        }

        List<RoomGroup> roomGroupAvailable = new ArrayList<>();
        List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);

        long availableRoomCount = roomGroup.getRooms().stream()
                .filter(availableRooms::contains)
                .count();

        long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);

        roomGroup.setAvailableRoomCount(availableRoomCount);
        roomGroup.setPriceDateTime(priceDateTime);

        if (availableRoomCount > 0) {
            roomGroupAvailable.add(roomGroup);
        }

        List<Review> reviews = reviewService.getReviewsByRoomGroupId(roomGroupId);
        model.addAttribute("bookingDto", bookingDto);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("roomGroup", roomGroup);
        model.addAttribute("priceDateTime", priceDateTime);
        model.addAttribute("reviews", reviews);
        model.addAttribute("newReply", new Reply());

        return "room-detail";
    }

    @PostMapping("/roomgroup/{roomGroupId}/{reviewId}/reply")
    public String addReply(@PathVariable("reviewId") Long reviewId,@PathVariable("roomGroupId") Long roomGroupId, @ModelAttribute Reply reply, Principal principal) {
        String email = principal.getName();
        Customer user = customerService.findByEmail2(email);
        Review review = reviewService.findById(reviewId);
        reply.setCreatedAt(LocalDateTime.now());
        reply.setUser(user);

        List<Reply> replies = review.getReplies();
        if(replies == null){
            replies = new ArrayList<>();
        }
        replies.add(reply);
        reply.setReview(review);
        reply.setUser(user);

        reviewService.save(review);
        return "redirect:/room-group-list-available/" + roomGroupId;
    }

    @GetMapping("/payment")
    public String handleBooking(@ModelAttribute BookingDto bookingDto,
                                @RequestParam("room") Long roomId, Model model, Principal principal) {

        if(principal == null){
            return "redirect:/login";
        }

        model.addAttribute("title", "Chi tiết Đặt phòng");
        Room selectedRoom = roomService.findById(roomId);
        long priceDateTime = roomGroupService.calculatePrice(bookingDto, selectedRoom.getRoomGroup());
        String gmail = principal.getName();
        Customer customer = customerService.findByEmail(gmail);
        Booking booking = new Booking();
        booking.setRoom(selectedRoom);
        booking.setUser(customer);
        booking.setCheckInDate(bookingDto.getCheckInDate() + " " + bookingDto.getCheckInTime() + ":00");
        booking.setCheckOutDate(bookingDto.getCheckOutDate() + " " + bookingDto.getCheckOutTime() + ":00");
        booking.setTotalPrice(priceDateTime);
        booking.setStatus(BookingStatus.PENDING);
        booking.setAmountHasPaid(priceDateTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        model.addAttribute("booking", booking);
        model.addAttribute("currentDate", currentDate);
        return "payment";
    }

    @PostMapping("/payment")
    public String confirmBooking(@ModelAttribute("booking") Booking booking, Model model, Principal principal) {
        System.out.println("Booking khi gửi lên: " + booking);

        if(principal == null){
            return "redirect:/login";
        }
        String gmail = principal.getName();
        Customer customer = customerService.findByEmail(gmail);

        System.out.println(booking);
        booking.setUser(customer);
        Booking bookingDB = bookingService.save(booking);
        Room room = roomService.findById(booking.getRoom().getRoomId());
        bookingDB.setRoom(room);
        bookingDB.setUser(customer);
        model.addAttribute("booking", bookingDB);

        return "payment-confirm";
    }
}
