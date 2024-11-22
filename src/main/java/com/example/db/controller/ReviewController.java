package com.example.db.controller;

import com.example.db.entity.Review;
import com.example.db.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/search")
    public List<Review> searchReviews(@RequestParam String keyword) {
        return reviewService.searchReviews(keyword);
    }

    @PostMapping
    public void createReview(@RequestBody Review review) {
        reviewService.createReview(review);
    }

    @PutMapping
    public void updateReview(@RequestBody Review review) {
        try{
            reviewService.updateReview(review);
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId, @RequestParam Long userId) {
        try{
            reviewService.deleteReview(reviewId, userId);
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
