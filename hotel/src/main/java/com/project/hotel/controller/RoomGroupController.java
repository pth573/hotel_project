package com.project.hotel.controller;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.RoomImage;
import com.project.hotel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class RoomGroupController {

    @Autowired
    private RoomGroupService roomGroupService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private FilesStorageService storageService;

    @Autowired
    private RoomImageService roomImageService;

    @Autowired
    public RoomGroupController(RoomGroupService roomGroupService, RoomService roomService, FilesStorageService storageService, RoomImageService roomImageService) {
        this.roomGroupService = roomGroupService;
        this.roomService = roomService;
        this.storageService = storageService;
        this.roomImageService = roomImageService;
    }

    @GetMapping("/roomgroup/list")
    public String getRoomGroupList(Model model) {
        List<RoomGroup> roomGroupList = roomGroupService.findAll();
        model.addAttribute("roomGroupList", roomGroupList);
        return "list-roomgroup";
    }

    @GetMapping("/roomgroup/add")
    public String showRoomGroupForm(Model model) {
        model.addAttribute("roomGroup", new RoomGroup());
        return "add-roomgroup";
    }

    @GetMapping("roomgroup/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        RoomGroup roomGroup = roomGroupService.findById(id);
        model.addAttribute("roomGroup", roomGroup);
        return "update-roomgroup";
    }

    @PostMapping("roomgroup/update")
    public String updateRoomGroup(@ModelAttribute RoomGroup roomGroup) {
        roomGroupService.save(roomGroup);
        return "redirect:/roomgroup/list";
    }
    @GetMapping("roomgroup/delete/{id}")
    public String deleteRoomGroup(@PathVariable("id") Long id) {
        RoomGroup roomGroup = roomGroupService.findById(id);
        roomImageService.deleteByRoomGroup(roomGroup);
        roomGroupService.deleteById(id);
        return "redirect:/roomgroup/list";
    }

    @GetMapping("/room_groups/{filename:.+}")
    public ResponseEntity<Resource> getRoomGroup(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/roomgroup/add")
    public String addRoomGroup(Model model,
                               @RequestParam("file") MultipartFile mainImage,
                               @RequestParam("files") MultipartFile[] fileImgList,
                               @ModelAttribute RoomGroup roomGroup) {
        String message = "";
        try {
            try{
                storageService.save(mainImage);
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
            Set<RoomImage> roomImageSet = new HashSet<>();
            for (MultipartFile file : fileImgList) {
                try{
                    storageService.save(file);
                    String additionalImageName = file.getOriginalFilename();
                    String additionalImageUrl = MvcUriComponentsBuilder
                            .fromMethodName(RoomGroupController.class, "getRoomGroup", additionalImageName)
                            .build().toString();
                    RoomImage roomImage = new RoomImage();
                    roomImage.setImageUrl(additionalImageUrl);
                    roomImage.setRoomGroup(roomGroup);
                    System.out.println(roomImage);
                    roomImageService.save(roomImage);
                    roomImageSet.add(roomImage);
                }catch (Exception e){
                    System.out.println("9");
                    String fileName = file.getOriginalFilename();;
                    String additionalImageUrl = MvcUriComponentsBuilder
                            .fromMethodName(RoomGroupController.class, "getRoomGroup", fileName)
                            .build().toString();
                    RoomImage roomImage = new RoomImage();
                    roomImage.setImageUrl(additionalImageUrl);
                    roomImage.setRoomGroup(roomGroup);
                    System.out.println(roomImage);
                    roomImageService.save(roomImage);
                    roomImageSet.add(roomImage);
                }
            }
            roomGroupService.save(roomGroup);
            message = "Đã tải lên nhóm phòng thành công: " + roomGroup.getGroupName();
            model.addAttribute("message", message);
        } catch (Exception e) {
            System.out.println("11");
            e.printStackTrace();
            model.addAttribute("message", "Không thể tải lên nhóm phòng do: " + e.getMessage());
        }
        return "redirect:/roomgroups";
    }


//    @PostMapping("/roomgroup/add")
//    public String addRoomGroup(Model model, @RequestParam("file") MultipartFile file, @ModelAttribute RoomGroup roomGroup) {
//        String message = "";
//        try {
//            try {
//                storageService.save(file);
//                String fileName = file.getOriginalFilename();
//                String url = MvcUriComponentsBuilder
//                        .fromMethodName(RoomGroupController.class, "getRoomGroup", fileName)
//                        .build().toString();
//                roomGroup.setImageUrl(url);
//
//            } catch (Exception e) {
//                String fileName = file.getOriginalFilename();
//                String url = MvcUriComponentsBuilder
//                        .fromMethodName(RoomGroupController.class, "getRoomGroup", fileName)
//                        .build().toString();
//                roomGroup.setImageUrl(url);
//            }
//            System.out.println(roomGroup);
//            roomGroupService.save(roomGroup);
//            message = "Uploaded the course successfully: " + file.getOriginalFilename();
//            model.addAttribute("message", message);
//        } catch (Exception e) {
//        }
//        return "redirect:/roomgroups";
//    }
//
}

