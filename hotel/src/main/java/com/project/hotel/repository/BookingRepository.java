package com.project.hotel.repository;

import com.project.hotel.model.entity.Booking;
import com.project.hotel.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingByUser(Customer customer);

//    // Tính doanh thu theo khoảng thời gian
//    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.checkInDate BETWEEN :startDate AND :endDate")
//    List<Long> calculateRevenueByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
//
//    // Tính tổng doanh thu
//    @Query("SELECT SUM(b.totalPrice) FROM Booking b")
//    List<Long> calculateTotalRevenue();

    @Query("SELECT b FROM Booking b WHERE b.checkInDate BETWEEN :startDate AND :endDate " +
            "AND b.checkOutDate BETWEEN :startDate AND :endDate")
    List<Booking> findBookingsByDateRange(@Param("startDate") String startDate,
                                          @Param("endDate") String endDate);

//    @Query("SELECT b FROM Booking b WHERE CAST(b.createdAt AS string) BETWEEN :startDate AND :endDate")
//    List<Booking> findBookingsByDateRange(@Param("startDate") String startDate,
//                                                 @Param("endDate") String endDate);

//
//    @Query("SELECT b FROM Booking b WHERE CAST(b.checkInDate AS string) BETWEEN :startDate AND :endDate")
//    List<Booking> findBookingsByDateRange(@Param("startDate") String startDate,
//                                          @Param("endDate") String endDate);

}
