package com.mparszewski.warehouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarehouseController {

    @GetMapping("/")
    public ResponseEntity<String> getStore() {
        return ResponseEntity.ok("Success");
    }

}
