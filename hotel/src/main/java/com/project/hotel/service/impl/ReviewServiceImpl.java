package com.project.hotel.service.impl;
import com.project.hotel.model.entity.Review;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.repository.ReviewRepository;

import com.project.hotel.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public Review findById(Long id) {
        Optional<Review> result = reviewRepository.findById(id);
        Review review = null;
        if(result.isPresent()){
            review = result.get();
        }
        else{
            throw new RuntimeException("Không thấy review có id: " + id);
        }
        return review;
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {

        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsByRoomGroupId(Long roomGroupId) {
        return reviewRepository.findReviewsByRoomGroupId(roomGroupId);
    }

    public List<Object[]> findReviewsWithSorting(String groupName, String sortBy, String hasReplies) {
        groupName = (groupName == null || groupName.isEmpty()) ? null : groupName;
        sortBy = (sortBy == null || sortBy.isEmpty()) ? "newest" : sortBy;

        Boolean hasRepliesFlag = null;
        if (hasReplies != null && !hasReplies.isEmpty()) {
            hasRepliesFlag = hasReplies.equals("true");
        }

        return reviewRepository.findReviewsWithDetails(groupName, sortBy, hasRepliesFlag);
    }

}
