package com.example.user.controller;

import com.example.user.entity.User;
import com.example.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    int retryCount = 1;

    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "reviewHotelBreaker",fallbackMethod = "reviewHotelFallback") // - CircuitBreaker pattern here
    //@Retry(name = "reviewHotelService", fallbackMethod = "reviewHotelFallback") // -Retry pattern here
    @RateLimiter(name = "userRateLimiter",fallbackMethod = "reviewHotelFallback") // -Rate limiter pattern here
    public ResponseEntity<User> getUser(@PathVariable String userId){
        LOGGER.info("Get single User Handler : UserController");
        LOGGER.info("Retry count : {}",retryCount);
        retryCount++;
        // get user from db with the help of user repository
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // Creating fallback method for circuit breaker (states of circuit breakers - CLOSED, OPEN, HALF_OPEN)
    public ResponseEntity<User> reviewHotelFallback(String userId, Exception ex){
        LOGGER.info("Fallback is executed due to service is down: {}",ex.getMessage());
        User user = User.builder()
                        .userId("000001")
                        .name("default")
                        .email("default@gmail.com")
                        .about("default about message")
                        .build();

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
