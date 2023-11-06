package com.sharif.ecomm.controller;

import com.sharif.ecomm.exception.OrderException;
import com.sharif.ecomm.model.Order;
import com.sharif.ecomm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrdersHandler() {
        List<Order> allOrders = orderService.getAllOrders();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> confirmOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        Order order = orderService.ShippedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliverOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        Order order = orderService.cancelledOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/delete")
    public ResponseEntity<String> deleteOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>("order deleted",HttpStatus.OK);
    }










}
