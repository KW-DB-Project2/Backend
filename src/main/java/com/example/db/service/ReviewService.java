package com.example.db.service;

import com.example.db.entity.Review;
import com.example.db.jdbc.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> searchReviews(String keyword){
        return reviewRepository.searchReview(keyword);
    }

    public void createReview(Review review) {
        reviewRepository.createReview(review);
    }

    public void updateReview(Review review) {
        int rowsAffected = reviewRepository.updateReview(review);
        if(rowsAffected == 0){
            throw new RuntimeException("You are not authorized to update this review Or There is no change");
        }
    }

    public void deleteReview(Long reviewId, Long userId) {
        int rowsAffected = reviewRepository.deleteReview(reviewId, userId);
        if(rowsAffected == 0){
            throw new RuntimeException("You are not authorized to delete this review Or There is no change");
        }

    }


}
