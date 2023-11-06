package com.sharif.ecomm.service;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.model.Rating;
import com.sharif.ecomm.model.User;
import com.sharif.ecomm.repository.RatingRepository;
import com.sharif.ecomm.utils.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RatingService {

    Rating createRating(RatingRequest re, User user) throws ProductException;

    List<Rating> getProductsRating(Long productId);



}
