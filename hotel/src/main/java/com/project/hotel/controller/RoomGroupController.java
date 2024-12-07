package com.project.hotel.controller;
import com.project.hotel.model.dto.BookingDto;
import com.project.hotel.model.dto.CustomerDto2;
import com.project.hotel.model.dto.RoomDTO2;
import com.project.hotel.model.entity.*;
import com.project.hotel.model.enumType.BedType;
import com.project.hotel.service.*;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomGroupController {

    private final RoomGroupService roomGroupService;
    private final RoomService roomService;
    private final FilesStorageService filesStorageService;
    private final RoomImageService roomImageService;
    private final BedService bedService;
    private final ServiceService serviceService;
    private final CustomerService customerService;
    private final ReviewService reviewService;

    @GetMapping("/room-group")
    public String roomGroupList(Model model, Principal principal) {
        CustomerUtils.getCustomerInfo(principal, customerService, model);
        List<RoomGroup> roomGroups = roomGroupService.findAll();
        model.addAttribute("roomGroups", roomGroups);
        for (RoomGroup roomGroup : roomGroups) {
            System.out.println(roomGroup);
        }
        return "room-group-user";
    }

//    @GetMapping("/admin/calendar")
//    public String showCalendar(Model model) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        // Lấy danh sách các phòng
//        List<Room> rooms = roomService.findAll();
//        List<RoomDTO2> roomDtos = new ArrayList<>();
//
//        for (Room room : rooms) {
//            RoomDTO2 roomDto = new RoomDTO2();
//            roomDto.setRoomId(room.getRoomId());
//            roomDto.setRoomName(room.getRoomName());
//            roomDto.setDescription(room.getDescription());
//
//            // Thêm booking của phòng vào DTO
//            List<BookingDto> bookingDtos = new ArrayList<>();
//            for (Booking booking : room.getBookings()) {
//                BookingDto bookingDto = new BookingDto();
//                bookingDto.setRoomId(room.getRoomId());
//                bookingDto.setCheckInDate(booking.getCheckInDate());
//                bookingDto.setCheckOutDate(booking.getCheckOutDate());
//                bookingDto.setAmountHasPaid(booking.getAmountHasPaid());
//                bookingDto.setTotalPrice(booking.getTotalPrice());
//                bookingDto.setStatus(booking.getStatus());
//
//                // Format ngày
//                String checkInDateStr = bookingDto.getCheckInDate();
//                LocalDate checkInDate = LocalDate.parse(checkInDateStr, formatter);
//                String checkOutDateStr = bookingDto.getCheckOutDate();
//                LocalDate checkOutDate = LocalDate.parse(checkOutDateStr, formatter);
//
//                bookingDto.setFormattedCheckInDate(java.util.Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                bookingDto.setFormattedCheckOutDate(java.util.Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//
//                // Lấy thông tin người dùng từ Booking và ánh xạ vào CustomerDto2
//                if (booking.getUser() != null) {
//                    CustomerDto2 customerDto = CustomerDto2.builder()
//                            .customerId(booking.getUser().getCustomerId())
//                            .email(booking.getUser().getEmail())
//                            .fullName(booking.getUser().getFullName())
//                            .phoneNumber(booking.getUser().getPhoneNumber())
//                            .build();
//                    bookingDto.setCustomerDto(customerDto);
//                }
//
//                bookingDtos.add(bookingDto);
//            }
//
//            roomDto.setBookingDtos(bookingDtos);
//            roomDtos.add(roomDto);
//        }
//
//        model.addAttribute("rooms", roomDtos);
//        model.addAttribute("currentMonth", LocalDate.now().getMonthValue());
//        model.addAttribute("daysInMonth", getDaysInMonth(LocalDate.now()));
//        return "admin-calendar";
//    }


    @GetMapping("/room-group/{roomGroupId}")
    public String roomGroupDetail(
            @PathVariable("roomGroupId") Long roomGroupId,
            Model model
    ) {
        RoomGroup roomGroup = roomGroupService.findById(roomGroupId);
        if (roomGroup == null) {
            return "redirect:/error?message=Room group not found";
        }
        List<Review> reviews = reviewService.getReviewsByRoomGroupId(roomGroupId);
        model.addAttribute("roomGroup", roomGroup);
        model.addAttribute("reviews", reviews);
        model.addAttribute("newReply", new Reply());
        return "room-group-detail";
    }

    @PostMapping("/roomgroup/detail/{roomGroupId}/{reviewId}/reply")
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
        return "redirect:/room-group/" + roomGroupId;
    }



//    @GetMapping("/room-group/{id}")
//    public String roomGroupDetail(Model model, Principal principal) {
//        CustomerUtils.getCustomerInfo(principal, customerService, model);
//        List<RoomGroup> roomGroups = roomGroupService.findAll();
//        model.addAttribute("roomGroups", roomGroups);
//        for (RoomGroup roomGroup : roomGroups) {
//            System.out.println(roomGroup);
//        }
////
////        if(bookingDto.getId() == 0){
////            System.out.println(1);
////            bookingDto = new BookingDto();
////            bookingDto.setCheckInDate(LocalDate.now().toString());
////            bookingDto.setCheckOutDate(LocalDate.now().plusDays(1).toString());
////            bookingDto.setCheckInTime("14:00");
////            bookingDto.setCheckOutTime("11:00");
////            bookingDto.setAdults(2);
////            bookingDto.setChildren(0);
////            List<RoomGroup> roomGroupAvailable = new ArrayList<>();
////            for (RoomGroup roomGroup : roomGroups) {
////                if(bookingDto.getChildren() + bookingDto.getAdults() <= roomGroup.getMaxOccupancy()){
////                    List<Room> availableRooms = roomService.findRoomAvailable(bookingDto);
////                    long availableRoomCount = roomGroup.getRooms().stream()
////                            .filter(room -> availableRooms.contains(room))
////                            .count();
////                    long priceDateTime = roomGroupService.calculatePrice(bookingDto, roomGroup);
////                    System.out.println(priceDateTime);
////                    System.out.println("Room Group: " + roomGroup.getGroupName() +
////                            " has " + availableRoomCount + " available rooms.");
////                    roomGroup.setAvailableRoomCount(availableRoomCount);
////                    roomGroup.setPriceDateTime(priceDateTime);
////
////                    if(availableRoomCount > 0){
////                        roomGroupAvailable.add(roomGroup);
////                    }
////                }
////            }
////            model.addAttribute("bookingDto", bookingDto);
////            model.addAttribute("roomGroups", roomGroupAvailable);
////            model.addAttribute("adults", bookingDto.getAdults());
////            model.addAttribute("children", bookingDto.getChildren());
////            model.addAttribute("checkInDate", bookingDto.getCheckInDate());
////            model.addAttribute("checkOutDate", bookingDto.getCheckOutDate());
////            model.addAttribute("checkInTime", bookingDto.getCheckInTime());
////            model.addAttribute("checkOutTime", bookingDto.getCheckOutTime());
////        }
////        model.addAttribute("adults", bookingDto.getAdults());
////        model.addAttribute("children", bookingDto.getChildren());
////        model.addAttribute("checkInDate", bookingDto.getCheckInDate());
////        model.addAttribute("checkOutDate", bookingDto.getCheckOutDate());
////        model.addAttribute("checkInTime", bookingDto.getCheckInTime());
////        model.addAttribute("checkOutTime", bookingDto.getCheckOutTime());
////        model.addAttribute("bookingDto", bookingDto);
//        return "room-group-user";
//    }


    @GetMapping("/room-group/list")
    public String getRoomGroupList(Model model) {
        List<RoomGroup> roomGroupList = roomGroupService.findAll();
        model.addAttribute("roomGroupList", roomGroupList);
        return "list-roomgroup";
    }

    @GetMapping("/room-group/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        RoomGroup roomGroup = roomGroupService.findById(id);
        System.out.println("1");
        System.out.println(roomGroup);
        model.addAttribute("roomGroup", roomGroup);
        return "update-roomgroup";
    }

    // chu y
    @PostMapping("/room-group/update/{id}")
    public String updateRoomGroup(@PathVariable("id") Long id, @RequestParam("file") MultipartFile mainImage,
                                  @RequestParam("files") MultipartFile[] fileImgList,
                                  @ModelAttribute RoomGroup roomGroup) {

        System.out.println(roomGroup);
        System.out.println("Hi");

        RoomGroup theRoomGroupFromDB = roomGroupService.findById(id);
        List<RoomImage> images = theRoomGroupFromDB.getImages();
        List<RoomImage> imagesToRemove = new ArrayList<>(images);
        for (RoomImage image : imagesToRemove) {
            theRoomGroupFromDB.getImages().remove(image);
            roomImageService.save(image);
        }

        try {
            try {
                filesStorageService.save(mainImage);
                String mainImageName = mainImage.getOriginalFilename();
                String mainImageUrl = MvcUriComponentsBuilder
                        .fromMethodName(RoomGroupController.class, "getRoomGroup", mainImageName)
                        .build().toString();
                theRoomGroupFromDB.setImageUrl(mainImageUrl);
            } catch (Exception e) {
                String fileName = mainImage.getOriginalFilename();
                String url = MvcUriComponentsBuilder
                        .fromMethodName(RoomGroupController.class, "getRoomGroup", fileName)
                        .build().toString();
                theRoomGroupFromDB.setImageUrl(url);
            }
            List<RoomImage> roomImageList = new ArrayList<>();
            for (MultipartFile file : fileImgList) {
                try {
                    filesStorageService.save(file);
                    String additionalImageName = file.getOriginalFilename();
                    String additionalImageUrl = MvcUriComponentsBuilder
                            .fromMethodName(RoomGroupController.class, "getRoomGroup", additionalImageName)
                            .build().toString();
                    RoomImage roomImage = new RoomImage();
                    theRoomGroupFromDB.setImageUrl(additionalImageUrl);
                    roomImage.setRoomGroup(theRoomGroupFromDB);
                    roomImageService.save(roomImage);
                    theRoomGroupFromDB.addImg(roomImage);

                } catch (Exception e) {
                    String fileName = file.getOriginalFilename();
                    String additionalImageUrl = MvcUriComponentsBuilder
                            .fromMethodName(RoomGroupController.class, "getRoomGroup", fileName)
                            .build().toString();
                    RoomImage roomImage = new RoomImage();
                    roomImage.setImageUrl(additionalImageUrl);
                    roomImage.setRoomGroup(theRoomGroupFromDB);
                    roomImageService.save(roomImage);
                    theRoomGroupFromDB.addImg(roomImage);
                }
            }
            theRoomGroupFromDB.setGroupName(roomGroup.getGroupName());
            theRoomGroupFromDB.setArea(roomGroup.getArea());
            theRoomGroupFromDB.setDescription(roomGroup.getDescription());
            theRoomGroupFromDB.setExtraHourPrice(roomGroup.getExtraHourPrice());
            theRoomGroupFromDB.setComboPrice2H(roomGroup.getComboPrice2H());
            theRoomGroupFromDB.setPricePerNight(roomGroup.getPricePerNight());
            theRoomGroupFromDB.setStandardOccupancy(roomGroup.getStandardOccupancy());
            roomGroupService.save(theRoomGroupFromDB);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/roomgroup/list";
    }



    @GetMapping("/room-group/delete/{id}")
    public String deleteRoomGroup(@PathVariable("id") Long id) {
        RoomGroup roomGroup = roomGroupService.findById(id);
        roomImageService.deleteByRoomGroup(roomGroup);
        roomGroupService.deleteById(id);
        return "redirect:/room group/list";
    }

    @GetMapping("/room_groups/{filename:.+}")
    public ResponseEntity<Resource> getRoomGroup(@PathVariable String filename) {
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @GetMapping("/room-group/add")
    public String showRoomGroupForm(Model model) {
        RoomGroup roomGroup = new RoomGroup();
        List<Bed> bedList = new ArrayList<>();
        BedType[] bedTypes = BedType.values();
        for (BedType bedType : bedTypes) {
            bedList.add(new Bed(bedType, 0));
        }
        roomGroup.setBeds(bedList);
        List<Service> services = serviceService.findAll();

        model.addAttribute("roomGroup", roomGroup);
        model.addAttribute("services", services);
        model.addAttribute("beds", bedList);
        return "admin-room-group";
    }

    @PostMapping("/room-group/add")
    public String addRoomGroup(Model model,
                               @RequestParam("file") MultipartFile mainImage,
                               @RequestParam("files") MultipartFile[] fileImgList,
                               @ModelAttribute RoomGroup roomGroup) {
        List<Bed> bedList = roomGroup.getBeds();
        for (Bed bed : bedList) {
            System.out.println(bed);
            bed.getRoomGroups().add(roomGroup);
            bedService.save(bed);
        }
        roomGroup.setBeds(bedList);
        String message = "";
        try {
            try{
                filesStorageService.save(mainImage);
                String mainImageName = mainImage.getOriginalFilename();
                String mainImageUrl = MvcUriComponentsBuilder
                        .fromMethodName(RoomGroupController.class, "getRoomGroup", mainImageName)
                        .build().toString();
                roomGroup.setImageUrl(mainImageUrl);
            }
            catch (Exception e){
                String fileName = mainImage.getOriginalFilename();
                String url = MvcUriComponentsBuilder
                        .fromMethodName(RoomGroupController.class, "getRoomGroup", fileName)
                        .build().toString();
                roomGroup.setImageUrl(url);
            }
            roomGroupService.save(roomGroup);
            List<RoomImage> roomImageList = new ArrayList<>();
            for (MultipartFile file : fileImgList) {
                try{
                    filesStorageService.save(file);
                    String additionalImageName = file.getOriginalFilename();
                    String additionalImageUrl = MvcUriComponentsBuilder
                            .fromMethodName(RoomGroupController.class, "getRoomGroup", additionalImageName)
                            .build().toString();
                    RoomImage roomImage = new RoomImage();
                    roomImage.setImageUrl(additionalImageUrl);
                    roomImage.setRoomGroup(roomGroup);
                    System.out.println(roomImage);
                    roomImageService.save(roomImage);
                    roomImageList.add(roomImage);
                }catch (Exception e){
                    String fileName = file.getOriginalFilename();;
                    String additionalImageUrl = MvcUriComponentsBuilder
                            .fromMethodName(RoomGroupController.class, "getRoomGroup", fileName)
                            .build().toString();
                    RoomImage roomImage = new RoomImage();
                    roomImage.setImageUrl(additionalImageUrl);
                    roomImage.setRoomGroup(roomGroup);
                    System.out.println(roomImage);
                    roomImageService.save(roomImage);
                    roomImageList.add(roomImage);
                }
            }
            roomGroupService.save(roomGroup);
            message = "Đã tải lên nhóm phòng thành công: " + roomGroup.getGroupName();
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Không thể tải lên nhóm phòng do: " + e.getMessage());
        }
        return "redirect:/room-group/list";
    }
}

