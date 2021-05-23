package com.mparszewski.apigateway.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {

    private String username;
    private String email;
    private Date tokenExpiration;
    private List<String> roles;
}
