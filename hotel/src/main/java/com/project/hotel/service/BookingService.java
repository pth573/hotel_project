package com.project.hotel.service;

import com.project.hotel.model.entity.Booking;
import com.project.hotel.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService{
    Booking save(Booking booking);

    List<Booking> findAll();

    void deleteById(Long id);
}
