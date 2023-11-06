package com.sharif.ecomm.repository;

import com.sharif.ecomm.model.Cart;
import com.sharif.ecomm.model.CartItem;
import com.sharif.ecomm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci from CartItem ci where ci.cart=:cart AND ci.product=:product AND ci.size =:size AND ci.userId=:userId")
    public CartItem isCartItemExists(
                @Param("cart")Cart cart,
                @Param("product")Product product,
                @Param("size") String size,
                @Param("userId") Long userId
            );
}
