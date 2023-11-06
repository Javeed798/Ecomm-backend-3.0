package com.sharif.ecomm.controller;

import com.sharif.ecomm.exception.CartItemException;
import com.sharif.ecomm.exception.UserException;
import com.sharif.ecomm.model.CartItem;
import com.sharif.ecomm.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/create")
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
        CartItem createdCartItem = cartItemService.createCartItem(cartItem);
        return new ResponseEntity<>(createdCartItem, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/{id}/update")
    public ResponseEntity<CartItem> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody CartItem cartItem) throws CartItemException, UserException {

        CartItem updatedCartItem = cartItemService.updateCartItem(userId, id, cartItem);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }

//    @GetMapping("/exists")
//    public ResponseEntity<CartItem> isCartItemExists(
//            @RequestParam Long cartId,
//            @RequestParam Long productId,
//            @RequestParam String size,
//            @RequestParam Long userId) {
//
//        CartItem cartItem = cartItemService.isCartItemExists(cartId, productId, size, userId);
//        if (cartItem != null) {
//            return new ResponseEntity<>(cartItem, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/{userId}/{cartItemId}/remove")
    public ResponseEntity<String> removeCartItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId) throws CartItemException, UserException {

        cartItemService.removeCartItem(userId, cartItemId);
        return new ResponseEntity<>("Cart item removed successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> findCartItemById(@PathVariable Long cartItemId) throws CartItemException {
        CartItem cartItem = cartItemService.findCartItemById(cartItemId);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
}