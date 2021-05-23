package com.mparszewski.auth.controller;

import com.mparszewski.auth.model.UserDto;
import com.mparszewski.auth.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserDetailsServiceImpl userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user-info")
    public UserDto getUserInfoFromToken(@RequestHeader("issuer-token") String token) {
        return userService.getUserInfoFromToken(token);
    }

}
