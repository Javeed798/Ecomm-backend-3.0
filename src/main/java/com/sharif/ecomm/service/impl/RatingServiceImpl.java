package com.sharif.ecomm.service.impl;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.model.Product;
import com.sharif.ecomm.model.Rating;
import com.sharif.ecomm.model.User;
import com.sharif.ecomm.repository.RatingRepository;
import com.sharif.ecomm.repository.UserRepository;
import com.sharif.ecomm.service.ProductService;
import com.sharif.ecomm.service.RatingService;
import com.sharif.ecomm.utils.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
