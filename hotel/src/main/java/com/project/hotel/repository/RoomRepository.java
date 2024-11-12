package com.project.hotel.repository;

import com.project.hotel.model.entity.Room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE NOT EXISTS ( " +
           "SELECT 1 FROM Booking b WHERE b.room = r " +
           "AND b.status = 'ACCEPTED' " +
           "AND ( " +
           "(b.checkInDate >= :endDate AND b.checkOutDate >= :startDate) " +  // Phòng đã được đặt từ endDate đến sau
           "OR (b.checkInDate <= :startDate AND b.checkOutDate >= :startDate) " + // Phòng đã được đặt từ trước startDate và kéo dài qua startDate
           "OR (b.checkInDate <= :endDate AND b.checkOutDate >= :endDate) " +    // Phòng đã được đặt từ trước endDate và kéo dài qua endDate
           "OR (b.checkInDate >= :startDate AND b.checkOutDate <= :endDate) " +  // Phòng đã được đặt trong khoảng từ startDate đến endDate
           ") " +
           ")")
    List<Room> findRoomAvailable(@Param("startDate") String startDate, 
                                  @Param("endDate") String endDate);
}

