package com.sharif.ecomm.service;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.model.AddItemRequest;
import com.sharif.ecomm.model.Cart;
import com.sharif.ecomm.model.User;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);
}
