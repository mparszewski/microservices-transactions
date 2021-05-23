package com.mparszewski.auth.controller;

import com.mparszewski.auth.model.ApplicationUser;
import com.mparszewski.auth.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<Void> signUp(@RequestBody ApplicationUser applicationUser) {
        userDetailsServiceImpl.saveUser(applicationUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(@AuthenticationPrincipal ApplicationUser applicationUser) {
        return new ResponseEntity<>("received response", HttpStatus.OK);
    }
}
