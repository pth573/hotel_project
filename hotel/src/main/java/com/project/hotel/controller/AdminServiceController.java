package com.project.hotel.controller;

import com.project.hotel.model.entity.Service;
import com.project.hotel.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/admin/service")
    public String getServiceList(Model model) {
        List<Service> serviceList = serviceService.findAll();
        model.addAttribute("serviceList", serviceList);
        return "admin-service";
    }

    @GetMapping("/admin/service/update/{serviceId}")
    public String showUpdateForm(@PathVariable Long serviceId, Model model) {
        Service service = serviceService.findById(serviceId);
        model.addAttribute("service", service);
        return "admin-service-update";
    }

    @PostMapping("/admin/service/update/{serviceId}")
    public String updateService(@PathVariable Long serviceId, @ModelAttribute Service updatedService) {
        serviceService.updateService(serviceId, updatedService);
        return "redirect:/admin/service";
    }

    @GetMapping("/admin/service/delete/{serviceId}")
    public String deleteService(@PathVariable Long serviceId) {
        serviceService.deleteById(serviceId);
        return "redirect:/admin/service";
    }
//
//    @GetMapping("/admin/service/add")
//    public String showServiceForm(Model model) {
//        Service service = new Service();
//        List<Service> serviceList = serviceService.findAll();
//        model.addAttribute("service", service);
//        return "admin-service";
//    }

    @PostMapping("/admin/service/add")
    public String addRoom(@ModelAttribute Service service) {
        serviceService.save(service);
        return "redirect:/admin/service";
    }
}
