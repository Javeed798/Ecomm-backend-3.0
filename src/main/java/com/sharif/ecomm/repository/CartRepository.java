package com.sharif.ecomm.repository;

import com.sharif.ecomm.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT c from Cart c where c.user.id=:userId")
    Cart findByUserId(@Param("userId") Long userId);
}
