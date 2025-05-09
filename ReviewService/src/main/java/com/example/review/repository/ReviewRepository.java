package com.example.review.repository;

import com.example.review.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review,String> {

    // custom finder methods
    List<Review> findByUserId(String userId);

    List<Review> findByHotelId(String hotelId);
}
