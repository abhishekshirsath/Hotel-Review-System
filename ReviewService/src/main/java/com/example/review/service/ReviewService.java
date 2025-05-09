package com.example.review.service;

import com.example.review.entity.Review;

import java.util.List;

public interface ReviewService {

    // create
    Review createReview(Review review);

    // get all reviews
    List<Review> getReviews();

    // get all reviews by userId
    List<Review> getReviewByUserId(String userId);

    // get all reviews by hotelId
    List<Review> getReviewByHotelId(String hotelId);
}
