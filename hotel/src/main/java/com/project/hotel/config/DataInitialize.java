package com.project.hotel.config;

import com.project.hotel.model.entity.Customer;
import com.project.hotel.model.entity.Role;
import com.project.hotel.repository.CustomerRepository;
import com.project.hotel.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitialize implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    void setUpRoles(){
        if(roleRepository.count() == 0) {
            roleRepository.saveAll(Arrays.asList(
                    Role.builder().name("ADMIN").build(),
                    Role.builder().name("CUSTOMER").build()
            ));
        }
    }

    void setUpCustomers(){
        if(customerRepository.count() == 0) {
            Customer admin = Customer.builder()
                    .email("admin@gmail.com")
                    .fullName("Admin")
                    .password(passwordEncoder.encode("123"))
                    .roles(Arrays.asList(roleRepository.findByName("ADMIN")))
                    .build();

            Customer customer = Customer.builder()
                    .email("customer@gmail.com")
                    .fullName("Customer")
                    .password(passwordEncoder.encode("123"))
                    .roles(Arrays.asList(roleRepository.findByName("CUSTOMER")))
                    .build();

            customerRepository.save(admin);
            customerRepository.save(customer);
        }
    }
    @Override
    public void run(String... args) throws Exception {
        setUpRoles();
        setUpCustomers();
    }
}
