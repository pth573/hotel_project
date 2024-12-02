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


    @Query("SELECT CASE " +
            "WHEN COUNT(b) > 0 THEN 0 " +
            "ELSE 1 " +
            "END " +
            "FROM Room r " +
            "LEFT JOIN Booking b ON r = b.room " +
            "WHERE r.roomId = :roomId " +
            "AND ((b.status = 'ACCEPTED' OR b.status = 'PENDING') " +
            "AND (" +
            "(b.checkInDate >= :endDate AND b.checkOutDate >= :startDate) OR " +
            "(b.checkInDate <= :startDate AND b.checkOutDate >= :startDate) OR " +
            "(b.checkInDate <= :endDate AND b.checkOutDate >= :endDate) OR " +
            "(b.checkInDate >= :startDate AND b.checkOutDate <= :endDate)" +
            "))")
    Integer checkRoomAvailabilityById(@Param("roomId") Long roomId,
                                      @Param("startDate") String startDate,
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




//    SET @startDate = '2024-11-29';
//    SET @endDate = '2024-11-31';
//
//-- Thực hiện truy vấn
//    SELECT
//    r.room_id,
//    r.room_name,
//    CASE
//    WHEN COUNT(b.booking_id) > 0 THEN 0  -- Trả về 0 nếu phòng đã được đặt
//    ELSE 1  -- Trả về 1 nếu phòng có sẵn
//    END AS room_status
//            FROM
//    Room r
//    LEFT JOIN
//    Booking b ON r.room_id = b.room_id
//    AND ((b.status = 'ACCEPTED' OR b.status = 'PENDING')
//    AND (
//        (b.check_in_date >= @endDate AND b.check_out_date >= @startDate) OR
//        (b.check_in_date <= @startDate AND b.check_out_date >= @startDate) OR
//            (b.check_in_date <= @endDate AND b.check_out_date >= @endDate) OR
//        (b.check_in_date >= @startDate AND b.check_out_date <= @endDate)
//            ))
//    GROUP BY r.room_id, r.room_name;


}

