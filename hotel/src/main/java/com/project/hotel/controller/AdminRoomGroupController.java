package com.project.hotel.controller;

import com.project.hotel.model.dto.*;
import com.project.hotel.model.entity.*;
import com.project.hotel.model.enumType.BedType;
import com.project.hotel.service.*;
import com.project.hotel.utils.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class AdminRoomGroupController {

    private final RoomGroupService roomGroupService;
    private final CustomerService customerService;
    private final RoomService roomService;
    private final ServiceService serviceService;
    private final BedService bedService;
    private final FilesStorageService filesStorageService;
    private final RoomImageService roomImageService;


    @GetMapping("/admin/room-group")
    public String roomGroup(Model model, Principal principal,  Locale locale) {
        model.addAttribute("title", "Admin Room Group");
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
        List<Bed> beds = bedService.findAll();
        if (roomGroup.getServices() == null) {
            roomGroup.setServices(new ArrayList<>());
        }
        Service service = new Service();
        model.addAttribute("roomGroupNew", roomGroup);
        model.addAttribute("roomGroup", roomGroup);

        model.addAttribute("services", services);
        model.addAttribute("service", service);
        model.addAttribute("roomGroups", roomGroups);
//        model.addAttribute("beds", beds);
        return "admin-room-group";
    }

//    @PostMapping("/admin/save-room-group")
//    public String saveRoomGroup(@ModelAttribute("new-roomGroup") RoomGroup roomGroup, Model model, RedirectAttributes redirectAttributes) {
//        try{
//            model.addAttribute("new-roomGroup", roomGroup);
//            redirectAttributes.addFlashAttribute("success", "Room Group saved successfully");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Room Group save failed");
//        }
//        roomGroupService.save(roomGroup);
//        return "redirect:/admin/room-group";
//    }

    @PostMapping("/admin/save-room-group")
    public String addRoomGroup(Model model,
                               @RequestParam("file") MultipartFile mainImage,
                               @RequestParam("files") MultipartFile[] fileImgList,
                               @ModelAttribute("roomGroupNew") RoomGroup roomGroup,
                               RedirectAttributes redirectAttributes
    ) {
        List<Bed> bedList = roomGroup.getBeds();
        for (Bed bed : bedList) {
            if(bed.getRoomGroups() == null){
                bed.setRoomGroups(new ArrayList<>());
            }
            bed.getRoomGroups().add(roomGroup);
            bedService.save(bed);
        }

        List<Service> serviceList = roomGroup.getServices();
        for (Service service : serviceList) {
            if(service.getRoomGroups() == null){
                service.setRoomGroups(new ArrayList<>());
            }
            service.getRoomGroups().add(roomGroup);
            serviceService.save(service);
        }
        roomGroup.setBeds(bedList);
        roomGroup.setServices(serviceList);
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
            redirectAttributes.addFlashAttribute("success", "Tạo thành công");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Tạo thất bại");
        }
        return "redirect:/admin/room-group";
    }



    @GetMapping("/admin/get-room-group/{id}")
    @ResponseBody
    public ResponseEntity<RoomGroupDTO> getRoomGroup(@PathVariable Long id, Model model) {
        System.out.println("Hi :" + id  );
        RoomGroup roomGroup = roomGroupService.findById(id);
        RoomGroupDTO roomGroupDTO = new RoomGroupDTO();
        roomGroupDTO.setGroupName(roomGroup.getGroupName());
        roomGroupDTO.setRoomGroupId(roomGroup.getRoomGroupId());
        roomGroupDTO.setArea(roomGroup.getArea());
        roomGroupDTO.setPricePerNight(roomGroup.getPricePerNight());
        roomGroupDTO.setComboPrice2H(roomGroup.getComboPrice2H());
        roomGroupDTO.setExtraHourPrice(roomGroup.getExtraHourPrice());
        roomGroupDTO.setDescription(roomGroup.getDescription());
        roomGroupDTO.setStandardOccupancy(roomGroup.getStandardOccupancy());
        roomGroupDTO.setMaxOccupancy(roomGroup.getMaxOccupancy());
        roomGroupDTO.setNumChildrenFree(roomGroup.getNumChildrenFree());
        roomGroupDTO.setExtraAdult(roomGroup.getExtraAdult());

        List<RoomDTO> roomDTOList = new ArrayList<>();
        for (Room room : roomGroup.getRooms()) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoomName(room.getRoomName());
            roomDTO.setRoomId(room.getRoomId());
            roomDTOList.add(roomDTO);
        }

        List<RoomImageDTO> roomImageDTOList = new ArrayList<>();
        for (RoomImage roomImage : roomGroup.getImages()){
            RoomImageDTO roomImageDTO = new RoomImageDTO();
            roomImageDTO.setImageUrl(roomImage.getImageUrl());
            roomImageDTO.setImageId(roomImage.getImageId());
            roomImageDTOList.add(roomImageDTO);
        }

        List<ServiceDTO> serviceDTOList = new ArrayList<>();
        for (Service service : roomGroup.getServices()) {
            ServiceDTO serviceDTO = new ServiceDTO();
            serviceDTO.setServiceName(service.getServiceName());
            serviceDTO.setServiceId(service.getServiceId());
            serviceDTO.setDescription(service.getDescription());
            serviceDTO.setPrice(service.getPrice());
            serviceDTOList.add(serviceDTO);
        }

        List<BedDTO> bedDTOList = new ArrayList<>();
        for(Bed bed : roomGroup.getBeds()){
            BedDTO bedDTO = new BedDTO();
            bedDTO.setBedNumber(bed.getBedNumber());
            bedDTO.setBedType(bed.getBedType());
            bedDTOList.add(bedDTO);
        }

        roomGroupDTO.setRooms(roomDTOList);
        roomGroupDTO.setImageUrl(roomGroup.getImageUrl());
        roomGroupDTO.setImages(roomImageDTOList);
        roomGroupDTO.setServices(serviceDTOList);
        roomGroupDTO.setBeds(bedDTOList);

        System.out.println(roomGroupDTO);
        System.out.println(ResponseEntity.ok(roomGroupDTO));
        model.addAttribute("roomGroup", roomGroup);
        return ResponseEntity.ok(roomGroupDTO);
    }

    @GetMapping("/admin/update-room-group/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        RoomGroup roomGroup = roomGroupService.findById(id);
        model.addAttribute("roomGroup", roomGroup);
        model.addAttribute("services", serviceService.findAll());
        return "admin-room-group-update";
    }


    @PostMapping("/admin/update-room-group/{id}")
    public String updateRoomGroup(@PathVariable("id") Long id, @RequestParam("file") MultipartFile mainImage,
                                  @RequestParam("files") MultipartFile[] fileImgList,
                                  @ModelAttribute("roomGroup") RoomGroup roomGroup
    ) {

        RoomGroup theRoomGroupFromDB = roomGroupService.findById(id);

        List<RoomImage> images = theRoomGroupFromDB.getImages();
        List<RoomImage> imagesToRemove = new ArrayList<>(images);
        for (RoomImage image : imagesToRemove) {
            theRoomGroupFromDB.getImages().remove(image);
            roomImageService.save(image);
        }

        List<Service> services = theRoomGroupFromDB.getServices();
        List<Service> servicesToRemove = new ArrayList<>(services);
        for (Service service : servicesToRemove) {
            theRoomGroupFromDB.getServices().remove(service);
            serviceService.save(service);
        }



        List<Bed> bedList = roomGroup.getBeds();
        for (Bed bed : bedList) {
            if(bed.getRoomGroups() == null){
                bed.setRoomGroups(new ArrayList<>());
            }
            bed.getRoomGroups().add(roomGroup);
            bedService.save(bed);
        }

        List<Service> serviceList = roomGroup.getServices();
        for (Service service : serviceList) {
            if(service.getRoomGroups() == null){
                service.setRoomGroups(new ArrayList<>());
            }
            service.getRoomGroups().add(roomGroup);
            serviceService.save(service);
        }
        roomGroup.setBeds(bedList);
        roomGroup.setServices(serviceList);

//        List<Service> services = theRoomGroupFromDB.getServices();
//        List<Service> servicesToRemove = new ArrayList<>(services);
//        for (Service service : servicesToRemove) {
//            theRoomGroupFromDB.getServices().remove(service);
//            serviceService.save(service);
//        }
//

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
                    roomImage.setImageUrl(additionalImageUrl);
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
        return "redirect:/admin/room-group";
    }


    @GetMapping("/admin/room-group/delete/{id}")
    public String deleteRoomGroup(@PathVariable("id") Long id) {
        RoomGroup roomGroup = roomGroupService.findById(id);
        roomImageService.deleteByRoomGroup(roomGroup);
        roomGroupService.deleteById(id);
        return "redirect:/admin/room-group";
    }

    @PostMapping("/admin/room-group/service/add")
    public String addRoom(@ModelAttribute Service service) {
        serviceService.save(service);
        return "redirect:/admin/room-group";
    }
}
