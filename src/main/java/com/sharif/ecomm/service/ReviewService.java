package com.sharif.ecomm.service;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.model.Review;
import com.sharif.ecomm.model.User;
import com.sharif.ecomm.utils.ReviewRequest;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest reviewRequest, User user) throws ProductException;

    List<Review> getAllReviews(Long productId);
}
