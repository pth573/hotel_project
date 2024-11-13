package com.project.hotel.utils;

import com.project.hotel.model.entity.Customer;
import com.project.hotel.model.entity.Role;
import com.project.hotel.service.CustomerService;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerUtils {

    public static void getCustomerInfo(Principal principal, CustomerService customerService, Model model) {
        if (principal == null) {
            return;
        }
        Customer customer = customerService.findByEmail(principal.getName());
        if (customer != null) {
            model.addAttribute("customer", customer);
            model.addAttribute("roles", extractRoles(customer));
        }
    }

    private static List<String> extractRoles(Customer customer) {
        return customer.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
