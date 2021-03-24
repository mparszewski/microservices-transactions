package com.mparszewski.auth.controller;

import com.mparszewski.auth.model.User;
import com.mparszewski.auth.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody User user) {
        userDetailsServiceImpl.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return new ResponseEntity<>("received response", HttpStatus.OK);
    }
}
