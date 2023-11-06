package com.sharif.ecomm.service;

import com.sharif.ecomm.exception.OrderException;
import com.sharif.ecomm.model.Address;
import com.sharif.ecomm.model.Order;
import com.sharif.ecomm.model.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, Address shippingAddress);
    Order findOrderById(Long orderId) throws OrderException;

    List<Order> userOrderHistory(Long userId);

    Order placedOrder(Long orderId) throws OrderException;

    Order confirmedOrder(Long orderId) throws OrderException;

    Order ShippedOrder(Long orderId) throws OrderException;

    Order deliveredOrder(Long orderId) throws OrderException;

    Order cancelledOrder(Long orderId) throws OrderException;

    List<Order> getAllOrders();

    void deleteOrder(Long orderId) throws OrderException;


}
