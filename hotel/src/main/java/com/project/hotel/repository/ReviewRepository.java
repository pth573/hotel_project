package com.project.hotel.repository;

import com.project.hotel.model.entity.Review;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT rev FROM Review rev " +
            "JOIN rev.booking b " +
            "JOIN b.room r " +
            "JOIN r.roomGroup rg " +
            "WHERE rg.roomGroupId = :roomGroupId")
    List<Review> findReviewsByRoomGroupId(Long roomGroupId);

//
//    @Query("SELECT r, rg.groupName FROM Review r " +
//            "JOIN r.booking b " +
//            "JOIN b.room rm " +
//            "JOIN rm.roomGroup rg " +
//            "WHERE (:groupName IS NULL OR rg.groupName LIKE %:groupName%) " +
//            "ORDER BY " +
//            "CASE WHEN :sortBy = 'newest' THEN r.createdAt END DESC, " +
//            "CASE WHEN :sortBy = 'oldest' THEN r.createdAt END ASC, " +
//            "CASE WHEN :sortBy = 'ratingAsc' THEN r.rating END ASC, " +
//            "CASE WHEN :sortBy = 'ratingDesc' THEN r.rating END DESC")
//    List<Object[]> findReviewsWithDetails(@Param("groupName") String groupName, @Param("sortBy") String sortBy);

    @Query("SELECT r, rg.groupName FROM Review r " +
            "JOIN r.booking b " +
            "JOIN b.room rm " +
            "JOIN rm.roomGroup rg " +
            "WHERE (:groupName IS NULL OR rg.groupName LIKE %:groupName%) " +
            "AND (:hasReplies IS NULL OR " +
            "     (:hasReplies = true AND size(r.replies) > 0) OR " +
            "     (:hasReplies = false AND size(r.replies) = 0)) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'newest' THEN r.createdAt END DESC, " +
            "CASE WHEN :sortBy = 'oldest' THEN r.createdAt END ASC, " +
            "CASE WHEN :sortBy = 'ratingAsc' THEN r.rating END ASC, " +
            "CASE WHEN :sortBy = 'ratingDesc' THEN r.rating END DESC")
    List<Object[]> findReviewsWithDetails(
            @Param("groupName") String groupName,
            @Param("sortBy") String sortBy,
            @Param("hasReplies") Boolean hasReplies
    );

}

