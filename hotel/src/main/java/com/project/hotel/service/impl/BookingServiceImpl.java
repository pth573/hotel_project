package com.project.hotel.service.impl;

import com.project.hotel.model.entity.Booking;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.model.entity.RoomImage;
import com.project.hotel.repository.BookingRepository;
import com.project.hotel.repository.RoomImageRepository;
import com.project.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }
}
