package com.sharif.ecomm.controller;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.exception.UserException;
import com.sharif.ecomm.model.Rating;
import com.sharif.ecomm.model.User;
import com.sharif.ecomm.service.RatingService;
import com.sharif.ecomm.service.UserService;
import com.sharif.ecomm.utils.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(
            @RequestBody RatingRequest req,
            @RequestHeader("Authorization") String jwt) throws ProductException, UserException {
        User user = this.userService.findUserProfileByJwt(jwt);
        Rating createdRating = ratingService.createRating(req, user);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId) {
        List<Rating> ratings = ratingService.getProductsRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}