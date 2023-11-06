package com.sharif.ecomm.controller;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.exception.UserException;
import com.sharif.ecomm.model.Review;
import com.sharif.ecomm.model.User;
import com.sharif.ecomm.service.ReviewService;
import com.sharif.ecomm.service.UserService;
import com.sharif.ecomm.utils.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(
            @RequestBody ReviewRequest reviewRequest,
            @RequestHeader("Authorization") String jwt) throws ProductException, UserException {
        User user = this.userService.findUserProfileByJwt(jwt);
        Review createdReview = reviewService.createReview(reviewRequest, user);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getAllProductReviews(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getAllReviews(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}