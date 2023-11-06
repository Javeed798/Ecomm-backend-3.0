package com.sharif.ecomm.service.impl;

import com.sharif.ecomm.model.OrderItem;
import com.sharif.ecomm.repository.OrderItemRepository;
import com.sharif.ecomm.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
