package com.mparszewski.order.service;

import com.mparszewski.order.model.Order;
import com.mparszewski.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void createNewOrder(Order order) {
        order.setStatus("Created");
        orderRepository.save(order);
    }

    public void bumpStatus(long id, String status) {
        orderRepository.findById(id).ifPresent(order -> order.setStatus(status));
    }
}
