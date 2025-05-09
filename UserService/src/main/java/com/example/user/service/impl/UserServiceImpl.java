package com.example.user.service.impl;

import com.example.user.entity.Hotel;
import com.example.user.entity.Review;
import com.example.user.entity.User;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.external.services.HotelService;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Override
    public User createUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        // fetch review of the above user from REVIEW-SERVICE
        // http://localhost:8083/review/users/773f801e-edf5-4f76-a9fd-dd78bd0f8b6d
        Review[] userReviews = restTemplate.getForObject("http://REVIEW-SERVICE/review/users/"+user.getUserId(), Review[].class);
        LOGGER.info("REVIEW-SERVICE data : {}",userReviews);
        List<Review> reviews = Arrays.stream(userReviews).toList();

        List<Review> reviewList = reviews.stream().map(review -> {

            // api call to hotel service to get the hotel
            // ResponseEntity<Hotel> hotelEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotel/"+review.getHotelId(), Hotel.class);
            // Hotel hotel = hotelEntity.getBody();
            Hotel hotel = hotelService.getHotel(review.getHotelId()); // FeignClient
            //LOGGER.info("status code : {}",hotelEntity.getStatusCode());

            // set the hotel to review
            review.setHotel(hotel);

            // return the reivew
            return review;
        }).collect(Collectors.toList());
        user.setReviews(reviewList);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }
}
