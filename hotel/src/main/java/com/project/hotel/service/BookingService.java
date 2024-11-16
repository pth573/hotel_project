package com.project.hotel.service;

import com.project.hotel.model.entity.Booking;
import com.project.hotel.model.entity.Customer;
import com.project.hotel.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public interface BookingService{
    Booking save(Booking booking);
    Booking findById(Long id);

    List<Booking> findAll();

    void deleteById(Long id);

    List<Booking> findByBookingUser(Customer user);
    public List<Booking> findBookingsByDateRange(String startDate, String endDate);
}
