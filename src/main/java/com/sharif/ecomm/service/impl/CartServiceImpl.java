package com.sharif.ecomm.service.impl;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.model.*;
import com.sharif.ecomm.repository.CartRepository;
import com.sharif.ecomm.repository.UserRepository;
import com.sharif.ecomm.service.CartItemService;
import com.sharif.ecomm.service.CartService;
import com.sharif.ecomm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemService cartItemsService;

    @Autowired
    private ProductService productService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        Cart save = cartRepository.save(cart);
        return save;
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());
        CartItem isPresent = cartItemsService.isCartItemExists(cart,product,req.getSize(),userId);
        if (isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            int price = req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());
            CartItem createCartItem = cartItemsService.createCartItem(cartItem);
            cart.getCartItems().add(createCartItem);
        }
        return "Item added to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        int totalPrice = 0;
        int totalDiscountedPrice=0;
        int totalItem = 0;

        for (CartItem cartItem:cart.getCartItems()){
            totalPrice+=cartItem.getPrice();
            totalDiscountedPrice+= cartItem.getDiscountedPrice();
            totalItem+= cartItem.getQuantity();
        }
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice-totalDiscountedPrice);
        return cartRepository.save(cart);
    }
}
