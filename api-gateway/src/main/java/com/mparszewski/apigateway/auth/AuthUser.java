package com.mparszewski.apigateway.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class AuthUser {

    private final String username;
    private final String email;
    private final Date tokenExpiration;
    private final List<String> roles;
}
