package com.sharif.ecomm.service.impl;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.model.Product;
import com.sharif.ecomm.model.Review;
import com.sharif.ecomm.model.User;
import com.sharif.ecomm.repository.ProductRepository;
import com.sharif.ecomm.repository.ReviewRepository;
import com.sharif.ecomm.service.ProductService;
import com.sharif.ecomm.service.ReviewService;
import com.sharif.ecomm.utils.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException {
        Product product = this.productService.findProductById(reviewRequest.getProductId());
        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setReview(reviewRequest.getReview());
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews(Long productId) {
        return reviewRepository.getAllProductReviews(productId);
    }
}
