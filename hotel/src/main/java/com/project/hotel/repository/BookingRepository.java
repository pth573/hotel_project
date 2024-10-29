package com.project.hotel.repository;

import com.project.hotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
