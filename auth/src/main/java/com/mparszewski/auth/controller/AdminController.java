package com.mparszewski.auth.controller;

import com.mparszewski.auth.model.ReviewRequestDto;
import com.mparszewski.auth.model.UserDto;
import com.mparszewski.auth.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private static final String ADMIN_REQUIRED = "hasRole('ROLE_ADMIN')";

    private final UserDetailsServiceImpl userService;

    @PreAuthorize(ADMIN_REQUIRED)
    @GetMapping("/user-info")
    public UserDto getUserInfoFromToken(@RequestHeader("issuer-token") String token) {
        return userService.getUserInfoFromToken(token);
    }

    @PreAuthorize(ADMIN_REQUIRED)
    @PostMapping("/reviews")
    public ResponseEntity<Void> addReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        userService.addReviewToList(reviewRequestDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ADMIN_REQUIRED)
    @GetMapping("/verification/{username}")
    public ResponseEntity<Boolean> hasVerification(@PathVariable String username) {
        return ResponseEntity.ok(userService.checkVerification(username));
    }


}
