package com.project.hotel.service.impl;
import com.project.hotel.model.dto.CustomerDto;
import com.project.hotel.model.entity.Customer;
import com.project.hotel.repository.RoleRepository;
import com.project.hotel.repository.CustomerRepository;
import com.project.hotel.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Override
    public Optional<Customer> findUserByEmail(String username) {
        return customerRepository.findStaffByEmail(username);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findStaffByEmail(email).orElseThrow(() -> new RuntimeException("not found customer " + email));
    }


    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customer.setFullName(customerDto.getFullName());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        customerRepository.save(customer);
    }

}
