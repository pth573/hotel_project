package com.project.hotel.service;
import com.project.hotel.model.entity.Review;
import com.project.hotel.model.entity.RoomGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    void save(Review service);
    Review findById(Long id);
    List<Review> findAll();
    void deleteById(Long id);
    List<Review> getReviewsByRoomGroupId(Long roomGroupId);
//    List<Object[]> findReviewsWithSorting(String groupName, String sortBy);
    List<Object[]> findReviewsWithSorting(String groupName, String sortBy, String hasReplies);
}
