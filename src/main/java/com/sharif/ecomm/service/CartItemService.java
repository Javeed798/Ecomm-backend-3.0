package com.sharif.ecomm.service;

import com.sharif.ecomm.exception.CartItemException;
import com.sharif.ecomm.exception.UserException;
import com.sharif.ecomm.model.Cart;
import com.sharif.ecomm.model.CartItem;
import com.sharif.ecomm.model.Product;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);

    CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;

//    if already exists increase quantity
    public CartItem isCartItemExists(Cart cart, Product product, String size,Long userId);

    void removeCartItem(Long userId, Long cartItemId) throws CartItemException,UserException;

    CartItem findCartItemById(Long cartItemId) throws CartItemException;




}
