package com.mparszewski.warehouse.service;

import com.mparszewski.warehouse.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;

    private final UserRemoteService userRemoteService;

    @Transactional
    public void addOrder(Product product, String username) {
    }
}
