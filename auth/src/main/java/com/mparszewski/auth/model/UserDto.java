package com.mparszewski.auth.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import static com.mparszewski.auth.model.Role.*;

@Setter
@Getter
public class UserDto {

    private String username;
    private String email;
    private Date tokenExpiration;
    private List<String> roles;

    public UserDto(ApplicationUser applicationUser) {
        this.username = applicationUser.getUsername();
        this.email = applicationUser.getEmail();
        this.roles = nameValues(applicationUser.getRoles());
    }
}
