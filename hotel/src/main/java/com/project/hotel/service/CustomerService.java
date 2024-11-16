package com.project.hotel.service;
import com.project.hotel.model.entity.Customer;
import com.project.hotel.model.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService {
    Optional<Customer> findUserByEmail(String username);
    void save(CustomerDto customerDto);
    void save(Customer customer);
    Customer findByEmail(String email);
    List<Customer> findAll();
    Customer findById(Long id);
    void deleteById(Long customerId);
}
