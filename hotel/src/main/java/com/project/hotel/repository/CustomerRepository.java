package com.project.hotel.repository;

import com.project.hotel.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findStaffByEmail(String email);

    Customer findCustomerByEmail(String email);


    @Query("select c from Customer c where c.email = :email")
    Customer findCustomerByUsername(@Param("email") String email);
}
