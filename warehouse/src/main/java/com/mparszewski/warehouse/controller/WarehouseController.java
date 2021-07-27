package com.mparszewski.warehouse.controller;

import com.mparszewski.warehouse.model.Product;
import com.mparszewski.warehouse.model.Review;
import com.mparszewski.warehouse.model.ReviewDto;
import com.mparszewski.warehouse.service.ProductService;
import com.mparszewski.warehouse.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final ProductService productService;

    private final ReviewService reviewService;

    @GetMapping("/view")
    public ResponseEntity<List<Product>> viewStore() {
        return ok(productService.getAllItems());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProducts(@RequestHeader String username,
                                            @RequestHeader List<String> roles,
                                            @RequestBody List<Product> products) {
        if (roles.contains("ROLE_ADMIN")) {
            productService.addProducts(products);
        } else {
            return ResponseEntity.status(SC_FORBIDDEN).build();
        }

        return accepted().build();
    }

    @PostMapping("/review")
    public ResponseEntity<Void> addReview(@RequestHeader String username,
                                          @RequestHeader List<String> roles,
                                          @RequestBody ReviewDto reviewDto) {
        if (roles.contains("ROLE_CLIENT")) {
            reviewService.addReview(reviewDto, username);
        } else {
            return ResponseEntity.status(SC_FORBIDDEN).build();
        }

        return accepted().build();
    }

    @PostMapping("/buy/{id}")
    public ResponseEntity<Void> buy(@RequestHeader String username,
                                    @RequestHeader List<String> roles,
                                    @PathVariable Long id) {
        if (roles.contains("ROLE_CLIENT")) {
        } else {
            return ResponseEntity.status(SC_FORBIDDEN).build();
        }

        return accepted().build();
    }
}
