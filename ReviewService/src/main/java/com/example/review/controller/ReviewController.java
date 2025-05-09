package com.example.review.controller;

import com.example.review.entity.Review;
import com.example.review.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review){
        Review savedReview = reviewService.createReview(review);
        LOGGER.info("saved user :{}",savedReview);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(){
        List<Review> reviews = reviewService.getReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Review>> getReviewByUserId(@PathVariable String userId){
        List<Review> reviewsByUserId = reviewService.getReviewByUserId(userId);
        return ResponseEntity.ok(reviewsByUserId);
    }

    @GetMapping("hotels/{hotelId}")
    public ResponseEntity<List<Review>> getReviewByHotelId(@PathVariable String hotelId){
        List<Review> reviewsByHotelId = reviewService.getReviewByHotelId(hotelId);
        return ResponseEntity.ok(reviewsByHotelId);
    }
    
}
