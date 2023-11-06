package com.sharif.ecomm.controller;

import com.sharif.ecomm.exception.ProductException;
import com.sharif.ecomm.exception.UserException;
import com.sharif.ecomm.model.AddItemRequest;
import com.sharif.ecomm.model.Cart;
import com.sharif.ecomm.model.User;
import com.sharif.ecomm.service.CartService;
import com.sharif.ecomm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService service;
    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = this.service.findUserProfileByJwt(jwt);
        Cart createdCart = cartService.createCart(user);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addCartItem(@PathVariable Long userId, @RequestBody AddItemRequest req) throws ProductException {
        String message = cartService.addCartItem(userId, req);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> findUserCart(@PathVariable Long userId) {
        Cart userCart = cartService.findUserCart(userId);
        return new ResponseEntity<>(userCart, HttpStatus.OK);
    }
}
