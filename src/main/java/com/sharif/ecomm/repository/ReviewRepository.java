package com.sharif.ecomm.repository;

import com.sharif.ecomm.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT r from Review r where r.product.id =:productId")
    List<Review> getAllProductReviews(@Param("productId") Long productId);
}

