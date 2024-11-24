package com.project.hotel.controller;
import com.project.hotel.model.entity.Customer;
import com.project.hotel.model.entity.Role;
import com.project.hotel.service.CustomerService;
import com.project.hotel.utils.CustomerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    // cua admin
    @Autowired
    private CustomerService customerService;
    @GetMapping("/chat-list")
    public String showChatBoxList(Model model, Principal principal) {
        Customer currentUser = getCurrentUser(principal);
        List<Customer> customers = customerService.findAllExceptCurrentUser(currentUser.getCustomerId());
        List<Customer> customerADMIN = new ArrayList<>();
        List<Customer> customerUSER = new ArrayList<>();
        for (Customer customer : customers) {
            boolean isAdmin = customer.getRoles().stream()
                    .anyMatch(role -> "ADMIN".equals(role.getName()));
            if (isAdmin) {
                customerADMIN.add(customer);
            } else {
                customerUSER.add(customer);
            }
        }

        boolean admin = false;
        for (Role role : currentUser.getRoles()) {
            if(role.getName().equals("ADMIN")){
                admin = true;
                break;
            }
        }

        if(admin){
            model.addAttribute("receivers", customerUSER);
        }
        else {
            model.addAttribute("receivers", customerADMIN);
        }


        return "chat/chat-list";
    }

    @GetMapping("/chat/{receiverId}")
    public String chatPageAdmin(@PathVariable("receiverId") Long id, Model model, Principal principal) {
        Customer user = customerService.findById(id);
        Customer currentUser = getCurrentUser(principal);
        CustomerUtils.getCustomerInfo(principal, customerService, model);
        List<Customer> customers = customerService.findAllExceptCurrentUser(currentUser.getCustomerId());
        List<Customer> customerADMIN = new ArrayList<>();
        List<Customer> customerUSER = new ArrayList<>();
        for (Customer customer : customers) {
            boolean isAdmin = customer.getRoles().stream()
                    .anyMatch(role -> "ADMIN".equals(role.getName()));
            if (isAdmin) {
                customerADMIN.add(customer);
            } else {
                customerUSER.add(customer);
            }
        }

        boolean admin = false;
        for (Role role : currentUser.getRoles()) {
            if(role.getName().equals("ADMIN")){
                admin = true;
                break;
            }
        }
        if(admin){
            model.addAttribute("receivers", customerUSER);
        }
        else {
            model.addAttribute("receivers", customerADMIN);
        }
        model.addAttribute("receiver", user);
        model.addAttribute("currentCustomer", currentUser);
        return "chat/chat-box";
    }


    @GetMapping("/chat/support")
    public String showChatBoxListUser(Model model, Principal principal) {
        Customer currentUser = getCurrentUser(principal);
//        model.addAttribute("receivers", customerService.findAllExceptCurrentUser(currentUser.getCustomerId()));
        List<Customer> customers = customerService.findAllExceptCurrentUser(currentUser.getCustomerId());
        List<Customer> customerADMIN = new ArrayList<>();
        List<Customer> customerUSER = new ArrayList<>();
        for (Customer customer : customers) {
            boolean isAdmin = customer.getRoles().stream()
                    .anyMatch(role -> "ADMIN".equals(role.getName()));
            if (isAdmin) {
                customerADMIN.add(customer);
            } else {
                customerUSER.add(customer);
            }
        }

        boolean admin = false;
        for (Role role : currentUser.getRoles()) {
            if(role.getName().equals("ADMIN")){
                admin = true;
                break;
            }
        }

        if(admin){
            model.addAttribute("receivers", customerUSER);
        }
        else {
            model.addAttribute("receivers", customerADMIN);
        }

        return "chat/chat-list-user";
    }


    // cua user
    @GetMapping("/chat/admin/{receiverId}")
    public String chatPageUser(@PathVariable("receiverId") Long id, Model model, Principal principal) {
        Customer user = customerService.findById(id);
        Customer currentUser = getCurrentUser(principal);
        CustomerUtils.getCustomerInfo(principal, customerService, model);
        List<Customer> customers = customerService.findAllExceptCurrentUser(currentUser.getCustomerId());
        List<Customer> customerADMIN = new ArrayList<>();
        List<Customer> customerUSER = new ArrayList<>();
        for (Customer customer : customers) {
            boolean isAdmin = customer.getRoles().stream()
                    .anyMatch(role -> "ADMIN".equals(role.getName()));
            if (isAdmin) {
                customerADMIN.add(customer);
            } else {
                customerUSER.add(customer);
            }
        }

        boolean admin = false;
        for (Role role : currentUser.getRoles()) {
            if(role.getName().equals("ADMIN")){
                admin = true;
                break;
            }
        }
        if(admin){
            model.addAttribute("receivers", customerUSER);
        }
        else {
            model.addAttribute("receivers", customerADMIN);
        }
        model.addAttribute("receiver", user);
        model.addAttribute("currentCustomer", currentUser);
        return "chat/chat-box-user";
    }

    private Customer getCurrentUser(Principal principal) {
        String username = principal.getName();
        return customerService.findByEmail2(username);
    }


}
