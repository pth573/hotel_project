package com.project.hotel.repository;

import com.project.hotel.model.entity.Room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE NOT EXISTS ( " +
           "SELECT 1 FROM Booking b WHERE b.room = r " +
           "AND ((b.status = 'ACCEPTED') OR (b.status = 'PENDING')) " +
           "AND ( " +
           "(b.checkInDate >= :endDate AND b.checkOutDate >= :startDate) " +
           "OR (b.checkInDate <= :startDate AND b.checkOutDate >= :startDate) " +
           "OR (b.checkInDate <= :endDate AND b.checkOutDate >= :endDate) " +
           "OR (b.checkInDate >= :startDate AND b.checkOutDate <= :endDate) " +
           ") " +
           ")")
    List<Room> findRoomAvailable(@Param("startDate") String startDate, 
                                  @Param("endDate") String endDate);


    @Query("SELECT r FROM Room r WHERE r.roomGroup.groupName = :groupName AND NOT EXISTS (" +
            "SELECT 1 FROM Booking b WHERE b.room = r " +
            "AND ((b.status = 'ACCEPTED') OR (b.status = 'PENDING')) " +
            "AND ( " +
            "(b.checkInDate >= :checkoutDate AND b.checkOutDate >= :checkinDate) " +
            "OR (b.checkInDate <= :checkinDate AND b.checkOutDate >= :checkinDate) " +
            "OR (b.checkInDate <= :checkoutDate AND b.checkOutDate >= :checkoutDate) " +
            "OR (b.checkInDate >= :checkinDate AND b.checkOutDate <= :checkoutDate) " +
            ") " +
            ")")
    List<Room> findAvailableRoomsByGroup(@Param("checkinDate") String checkInDate,
                                         @Param("checkoutDate") String checkOutDate,
                                         @Param("groupName") String groupName);

}

