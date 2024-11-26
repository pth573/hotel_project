package com.project.hotel.service.impl;

import com.project.hotel.model.entity.*;
import com.project.hotel.model.enumType.BookingStatus;
import com.project.hotel.repository.BookingRepository;
import com.project.hotel.repository.RoomImageRepository;
import com.project.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findById(Long id) {
        Optional<Booking> result = bookingRepository.findById(id);
        Booking booking = null;
        if(result.isPresent()){
            booking = result.get();
        }
        else{
            throw new RuntimeException("Không thấy booking có id: " + id);
        }
        return booking;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> findByBookingUser(Customer user) {
        return bookingRepository.findBookingByUser(user);
    }

    @Override
    public List<Booking> findBookingsByDateRange(String startDate, String endDate) {
        return bookingRepository.findBookingsByDateRange(startDate, endDate);
    }

    @Override
    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        existingBooking.setCheckInDate(updatedBooking.getCheckInDate());
        existingBooking.setCheckOutDate(updatedBooking.getCheckOutDate());
        existingBooking.setTotalPrice(updatedBooking.getTotalPrice());
        existingBooking.setAmountHasPaid(updatedBooking.getAmountHasPaid());
        BookingStatus bookingStatus = updatedBooking.getStatus();
        existingBooking.setStatus(bookingStatus);
        return bookingRepository.save(existingBooking);
    }

}
