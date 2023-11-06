package com.sharif.ecomm.controller;

import com.sharif.ecomm.exception.OrderException;
import com.sharif.ecomm.exception.UserException;
import com.sharif.ecomm.model.Address;
import com.sharif.ecomm.model.Order;
import com.sharif.ecomm.model.User;
import com.sharif.ecomm.service.OrderService;
import com.sharif.ecomm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
                                             @RequestHeader("Authorization") String jwt
                                             ) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = this.orderService.createOrder(user, shippingAddress);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = this.orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

//    @GetMapping("/${id}")
//    public ResponseEntity<Order> findOrderById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
//        User user = userService.findUserProfileByJwt(jwt);
//        Order order = orderService.findOrderById(user.getId());
//        return new ResponseEntity<>(order,HttpStatus.OK);
//    }

}
