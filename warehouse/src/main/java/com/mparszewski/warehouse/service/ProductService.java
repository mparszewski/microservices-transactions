package com.mparszewski.warehouse.service;

import com.mparszewski.warehouse.model.Product;
import com.mparszewski.warehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final UserRemoteService userRemoteService;

/*    @Transactional
    public void addOrder(Long productId, String username) {
        productRepository.findById(productId)
    }*/

    public List<Product> getAllItems() {
        return productRepository.findAll();
    }

    public void addProducts(List<Product> products) {
        productRepository.saveAll(products);
    }

}
