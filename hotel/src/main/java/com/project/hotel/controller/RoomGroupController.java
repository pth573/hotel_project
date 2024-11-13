package com.project.hotel.controller;
import com.project.hotel.model.entity.Bed;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.RoomImage;
import com.project.hotel.model.enumType.BedType;
import com.project.hotel.service.*;
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
//            theRoomGroupFromDB.setBedType(roomGroup.getBedType());
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
        return "redirect:/roomgroup/list";
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
        model.addAttribute("roomGroup", roomGroup);
        model.addAttribute("beds", bedList);  // Thêm danh sách giường vào model để sử dụ
        return "add-roomgroup";
    }

    @PostMapping("/room-group/add")
    public String addRoomGroup(Model model,
                               @RequestParam("file") MultipartFile mainImage,
                               @RequestParam("files") MultipartFile[] fileImgList,
                               @ModelAttribute RoomGroup roomGroup) {
        List<Bed> bedList = roomGroup.getBeds();
        for (Bed bed : bedList) {
            System.out.println(bed);
            bed.addRoomGroup(roomGroup);
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

