package com.example.db.controller;

import com.example.db.dto.ReviewDTO;
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
    public List<ReviewDTO> searchReviews(@RequestParam(name = "keyword") String keyword) {
        return reviewService.searchReviews(keyword);
    }

    @GetMapping("/product/{productId}")
    public List<ReviewDTO> getReviewsByProductId(@PathVariable("productId") Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    @GetMapping("/{reviewId}")
    public Review getDetailReview(@PathVariable("reviewId") Long reviewId) {
        return reviewService.getReviewByReviewId(reviewId);
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
    public void deleteReview(@PathVariable("reviewId") Long reviewId, @RequestParam("userId") Long userId) {
        try{
            reviewService.deleteReview(reviewId, userId);
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
