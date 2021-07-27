package com.mparszewski.order.controller;

import com.mparszewski.order.model.Order;
import com.mparszewski.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Void> createOrder(@RequestBody Order order) {
        orderService.createNewOrder(order);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/status")
    public ResponseEntity<Void> bumpStatus(@RequestParam("status") String status,
                                           @RequestParam("order") Long orderId) {
        orderService.bumpStatus(orderId, status);
        return ResponseEntity.ok().build();
    }
}
